package com.example.support.feature.synonyms.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseGameViewModel
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.util.Constants
import com.example.support.core.util.GameManager
import com.example.support.core.util.HapticFeedbackManager
import com.example.support.core.util.ResultCore
import com.example.support.core.util.timer.GameTimerController
import com.example.support.core.util.timer.TimerManager
import com.example.support.feature.synonyms.model.SynonymOption
import com.example.support.feature.synonyms.model.SynonymsEvent
import com.example.support.feature.synonyms.model.SynonymsResult
import com.example.support.feature.synonyms.model.SynonymsState
import com.example.support.feature.synonyms.presentation.repository.SynonymsGameManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SynonymsViewModel @Inject constructor(
    navigator: Navigator,
    timerManager: TimerManager,
    gameTimerController: GameTimerController,
    private val hapticFeedbackManager: HapticFeedbackManager,
    private val gameManager: SynonymsGameManager
) : BaseGameViewModel<SynonymsState, SynonymsEvent>(
    SynonymsState(),
    navigator,
    timerManager,
    gameTimerController
), SynonymsController{

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

    override fun initializeGame() {
        updateState(uiState.value.copy(result = SynonymsResult.Loading))
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

    override fun toggleSelection(index: Int) {
        val currentOptions = uiState.value.options
        val selectedCount = currentOptions.count { it.isSelected }

        val updatedOptions = currentOptions.mapIndexed { i, option ->
            if (i == index && option.isCorrect == null) {
                if (!option.isSelected && selectedCount >= 3) {
                    option
                } else {
                    option.copy(isSelected = !option.isSelected)
                }
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

    override fun getGameManager(): GameManager = gameManager
    override fun handleTimeExpired(score: Int) {
        gameManager.saveScore(score)
        viewModelScope.launch {
            navigator.navigate(NavigationEvent.Navigate(
                NavigationItem.GameCompletion.route
            ))
            delay(300)
            resetGame()
        }
    }

    override fun getCurrentScore(): Int = uiState.value.score

    override fun isGameStarted(): Boolean = uiState.value.hasStarted

    override fun createInitialState(): SynonymsState = SynonymsState()
}