package com.example.support.feature.synonyms.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseGameViewModel
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.util.Constants
import com.example.support.core.util.HapticFeedbackManager
import com.example.support.core.util.ResultCore
import com.example.support.core.util.TimerManager
import com.example.support.feature.factopinion.model.FactOpinionResult
import com.example.support.feature.factopinion.model.FactOpinionState
import com.example.support.feature.synonyms.model.SynonymOption
import com.example.support.feature.synonyms.model.SynonymsEvent
import com.example.support.feature.synonyms.model.SynonymsResult
import com.example.support.feature.synonyms.model.SynonymsState
import com.example.support.feature.synonyms.presentation.repository.SynonymsGameManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SynonymsViewModel @Inject constructor(
    navigator: Navigator,
    private val timerManager: TimerManager,
    private val hapticFeedbackManager: HapticFeedbackManager,
    private val gameManager: SynonymsGameManager
) : BaseGameViewModel<SynonymsState, SynonymsEvent>(SynonymsState(), navigator), SynonymsController{

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

    override fun onEvent(event: SynonymsEvent) {
        when (event) {
            is SynonymsEvent.StartGame -> {
                if (!uiState.value.hasStarted) {
                    updateState(uiState.value.copy(hasStarted = true))
                    startGame()
                    timerManager.startTimer(viewModelScope, Constants.GAME_TIMER_DURATION)
                }
            }
            is SynonymsEvent.AnswerQuestion -> {
                checkAnswer()
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

    override fun toggleSelection(index: Int) {
        val updatedOptions = uiState.value.options.mapIndexed { i, option ->
            if (i == index && option.isCorrect == null) {
                option.copy(isSelected = !option.isSelected)
            } else option
        }

        updateState(uiState.value.copy(options = updatedOptions))
    }

    private fun checkAnswer() {
        val currentState = uiState.value
        val correctAnswers = currentState.correctAnswers.toSet()

        val updatedOptions = currentState.options.map { option ->
            if (option.isSelected) {
                option.copy(isCorrect = correctAnswers.contains(option.text))
            } else {
                option
            }
        }

        val selectedAnswers = updatedOptions.filter { it.isSelected }.map { it.text }.toSet()
        val numCorrectSelected = selectedAnswers.count { correctAnswers.contains(it) }

        val scoreIncrement = numCorrectSelected * 10

        updateState(currentState.copy(
            options = updatedOptions,
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


    private fun startGame() {
        viewModelScope.launch {
            when (val init = gameManager.loadShuffledIdsIfNeeded()) {
                is ResultCore.Failure -> {
                    updateState(uiState.value.copy(result = SynonymsResult.Error(init.message)))
                    return@launch
                }

                is ResultCore.Success -> loadNextQuestion()
            }
        }
    }

    private fun loadNextQuestion() {
        viewModelScope.launch {
            when (val result = gameManager.getNextQuestion()) {
                is ResultCore.Success -> {
                    val combinedWords = (result.data.otherWords + result.data.synonyms)
                        .shuffled()
                        .map { word ->
                            SynonymOption(
                                text = word,
                            )
                        }

                    updateState(
                        uiState.value.copy(
                            category = result.data.category,
                            mainWord = result.data.word,
                            correctAnswers = result.data.synonyms,
                            result = SynonymsResult.Success,
                            options = combinedWords
                        )
                    )
                }

                is ResultCore.Failure -> {
                    updateState(uiState.value.copy(result = SynonymsResult.Error(result.message)))
                }
            }
        }
    }


    private fun reset() {
        timerManager.resetTimer(viewModelScope)
        gameManager.reset()
        updateState(
            SynonymsState()
        )
    }
}