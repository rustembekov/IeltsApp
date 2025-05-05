package com.example.support.feature.phrasalverbs.presentation.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseGameViewModel
import com.example.support.core.data.GamePreferences
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.util.Constants
import com.example.support.core.util.GameManager
import com.example.support.core.util.HapticFeedbackManager
import com.example.support.core.util.ResultCore
import com.example.support.core.util.timer.GameTimerController
import com.example.support.core.util.timer.TimerManager
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsEventEvent
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsResult
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsState
import com.example.support.feature.phrasalverbs.presentation.repository.PhrasalVerbsGameManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PhrasalVerbsViewModel @Inject constructor(
    navigator: Navigator,
    timerManager: TimerManager,
    gameTimerController: GameTimerController,
    private val gameManager: PhrasalVerbsGameManager,
    private val gamePreferences: GamePreferences,
    private val vibrationManager: HapticFeedbackManager
) : BaseGameViewModel<PhrasalVerbsState, PhrasalVerbsEventEvent>(
    PhrasalVerbsState(),
    navigator,
    timerManager,
    gameTimerController
), PhrasalVerbsController {

    override fun onEvent(event: PhrasalVerbsEventEvent) {
        when (event) {
            is PhrasalVerbsEventEvent.StartGame -> {
                startGame()
            }

            is PhrasalVerbsEventEvent.Answer -> {
                checkAnswer()
            }
        }
    }

    override fun initializeGame() {
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
                    Timber.tag("PhrasalVerbsViewModel").d("Answer: %s", uiState.value.answer)
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

    override fun getGameManager(): GameManager = gameManager

    override fun handleTimeExpired(score: Int) {
        gameManager.saveScore(score)
        gamePreferences.setLastPlayedGame(Constants.PHRASAL_VERBS_GAME)
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

    override fun createInitialState(): PhrasalVerbsState = PhrasalVerbsState()

}