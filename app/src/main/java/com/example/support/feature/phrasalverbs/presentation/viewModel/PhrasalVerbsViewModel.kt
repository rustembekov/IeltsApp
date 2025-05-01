package com.example.support.feature.phrasalverbs.presentation.viewModel

import android.util.Log
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
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsEventEvent
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsResult
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsState
import com.example.support.feature.phrasalverbs.presentation.repository.PhrasalVerbsGameManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhrasalVerbsViewModel @Inject constructor(
    navigator: Navigator,
    private val gameManager: PhrasalVerbsGameManager,
    private val timerManager: TimerManager,
    private val vibrationManager: HapticFeedbackManager

) : BaseGameViewModel<PhrasalVerbsState, PhrasalVerbsEventEvent>(PhrasalVerbsState(), navigator),
    PhrasalVerbsController {
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

    override fun onEvent(event: PhrasalVerbsEventEvent) {
        when (event) {
            is PhrasalVerbsEventEvent.StartGame -> {
                if (!uiState.value.hasStarted) {
                    updateState(uiState.value.copy(hasStarted = true))
                    startGame()
                    timerManager.startTimer(viewModelScope, Constants.GAME_TIMER_DURATION)
                }
            }

            is PhrasalVerbsEventEvent.Answer -> {
                checkAnswer()
            }
        }
    }

    private fun startGame() {
        updateState(uiState.value.copy(result = PhrasalVerbsResult.Loading))
        viewModelScope.launch {
            when (val init = gameManager.loadShuffledIdsIfNeeded()) {
                is ResultCore.Failure -> {
                    updateState(uiState.value.copy(result = PhrasalVerbsResult.Error(init.message)))
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
                    updateState(
                        uiState.value.copy(
                            currentQuestion = result.data.text,
                            answer = result.data.answer,
                            result = PhrasalVerbsResult.Success,
                            userInput = ""
                        )
                    )
                    Log.d("PhrasalVerbsViewModel", "Answer: ${uiState.value.answer}")
                }

                is ResultCore.Failure -> {
                    updateState(uiState.value.copy(result = PhrasalVerbsResult.Error(result.message)))
                }
            }
        }
    }

    private fun checkAnswer() {
        val currentState = uiState.value
        val isCorrect = currentState.userInput == currentState.answer

        if (isCorrect) {
            updateState(currentState.copy(
                isShownCorrectAnswer = true,
                score = currentState.score + 30
            ))

            viewModelScope.launch {
                delay(1500L)
                updateState(uiState.value.copy(isShownCorrectAnswer = false))
            }
        } else {
            vibrationManager.vibrate(200)
        }

        timerManager.pauseTimer()

        loadNextQuestion()

        timerManager.resumeTimer()
    }

    override fun onInputChange(input: String) {
        updateState(
            uiState.value.copy(
                userInput = input
            )
        )
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
            PhrasalVerbsState()
        )
    }
}