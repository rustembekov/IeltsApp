package com.example.support.feature.essaybuilder.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseGameViewModel
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.util.HapticFeedbackManager
import com.example.support.core.util.ResultCore
import com.example.support.core.util.TimerManager
import com.example.support.feature.essaybuilder.model.EssayBuilderEvent
import com.example.support.feature.essaybuilder.model.EssayBuilderState
import com.example.support.feature.essaybuilder.model.EssayBuilderState.EssayBuilderResult
import com.example.support.feature.essaybuilder.presentation.data.EssayBuilderGame
import com.example.support.feature.essaybuilder.presentation.repository.EssayBuilderGameManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EssayBuilderViewModel @Inject constructor(
    navigator: Navigator,
    private val gameManager: EssayBuilderGameManager,
    private val timerManager: TimerManager,
    private val hapticFeedbackManager: HapticFeedbackManager
) : BaseGameViewModel<EssayBuilderState, EssayBuilderEvent>(EssayBuilderState(), navigator),
    EssayBuilderController {

    init {
        timerManager.timerFlow
            .onEach { time ->
                time?.let {
                    updateState(uiState.value.copy(timer = it))

                    if (it == 0 && uiState.value.hasStarted) {
                        val score = uiState.value.score
                        gameManager.saveScore(score)
                        navigator.navigate(NavigationEvent.Navigate(NavigationItem.GameCompletion.route))
                        delay(300)
                        reset()
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onEvent(event: EssayBuilderEvent) {
        when (event) {
            is EssayBuilderEvent.StartGame -> {
                if (!uiState.value.hasStarted) {
                    updateState(uiState.value.copy(hasStarted = true))
                    startGame()
                    timerManager.startTimer(viewModelScope, uiState.value.timer)
                }
            }

            is EssayBuilderEvent.AnswerQuestion -> {
                checkAnswer()
            }

        }
    }

    private fun startGame() {
        updateState(uiState.value.copy(result = EssayBuilderResult.Loading))
        viewModelScope.launch {
            when (val init = gameManager.loadShuffledIdsIfNeeded()) {
                is ResultCore.Failure -> {
                    updateState(uiState.value.copy(result = EssayBuilderResult.Error(init.message)))
                    return@launch
                }

                is ResultCore.Success -> loadNextQuestion()
            }
        }
    }

    private fun loadNextQuestion() {
        viewModelScope.launch {
            when (val result: ResultCore<EssayBuilderGame> = gameManager.getNextQuestion()) {
                is ResultCore.Success -> {
                    val game = result.data

                    val parts = game.questionParts.map {
                        when (it.type) {
                            "text" -> EssayBuilderState.Part.Text(it.value ?: "")
                            "blank" -> EssayBuilderState.Part.Blank(it.index ?: 0)
                            else -> throw IllegalArgumentException("Unknown part type: ${it.type}")
                        }
                    }

                    val currentBlanks = List(game.correctAnswers.size) { null }

                    updateState(
                        uiState.value.copy(
                            questionParts = parts,
                            options = game.options.map { EssayBuilderState.OptionUiModel(word = it) },
                            currentBlanks = currentBlanks,
                            result = EssayBuilderResult.Success,
                            correctAnswers = game.correctAnswers
                        )
                    )
                }

                is ResultCore.Failure -> {
                    updateState(uiState.value.copy(result = EssayBuilderResult.Error(result.message)))
                }
            }
        }
    }

    private fun checkAnswer() {
        val currentState = uiState.value
        val correctAnswers = currentState.correctAnswers.toSet()

        val updatedBlanks = currentState.currentBlanks.map { blank ->
            blank?.copy(
                isCorrect = correctAnswers.contains(blank.word),
                isSelected = true
            )
        }

        val selectedAnswers = updatedBlanks.mapNotNull { it?.word }.toSet()
        val numCorrectSelected = selectedAnswers.count { correctAnswers.contains(it) }

        val scoreIncrement = numCorrectSelected * 10

        updateState(currentState.copy(
            currentBlanks = updatedBlanks,
            score = currentState.score + scoreIncrement
        ))

        timerManager.pauseTimer()

        if (selectedAnswers == correctAnswers) {
            viewModelScope.launch {
                delay(1500L)
                loadNextQuestion()
                timerManager.resumeTimer()
            }
        } else {
            hapticFeedbackManager.vibrate(200)

            viewModelScope.launch {
                delay(1500L)
                loadNextQuestion()
                timerManager.resumeTimer()
            }
        }
    }


    override fun onWordClick(word: String) {
        val state = uiState.value

        val isSelected = state.selectedWord == word
        val newOptions = state.options.map {
            when {
                it.word == word -> it.copy(isSelected = !isSelected)
                else -> it.copy(isSelected = false)
            }
        }

        updateState(
            state.copy(
                options = newOptions,
                selectedWord = if (isSelected) null else word
            )
        )
    }


    override fun onBlankClick(index: Int) {
        val state = uiState.value
        val selectedWord = state.selectedWord
        val currentBlanks = state.currentBlanks.toMutableList()

        val removed = currentBlanks[index]
        if (removed != null) {
            val updatedOptions = state.options.map {
                if (it.word == removed.word) it.copy(isUsed = false) else it
            }
            currentBlanks[index] = null

            updateState(
                state.copy(
                    currentBlanks = currentBlanks,
                    options = updatedOptions
                )
            )
        } else if (selectedWord != null) {
            if (state.options.any { it.word == selectedWord && !it.isUsed }) {
                currentBlanks[index] = EssayBuilderState.BlanksUiModel(word = selectedWord)

                val updatedOptions = state.options.map {
                    if (it.word == selectedWord) it.copy(isUsed = true, isSelected = false) else it
                }

                updateState(
                    state.copy(
                        currentBlanks = currentBlanks,
                        options = updatedOptions,
                        selectedWord = null
                    )
                )
            }
        }
    }

    override fun onPauseClicked() {
        super.onPauseClicked()
        timerManager.pauseTimer()
    }

    override fun onResumePauseDialog() {
        super.onResumePauseDialog()
        timerManager.resumeTimer()
    }

    override fun onCleared() {
        super.onCleared()
        timerManager.stopTimer()
    }

    private fun reset() {
        timerManager.resetTimer(viewModelScope)
        gameManager.reset()
        updateState(
            EssayBuilderState()
        )
    }
}
