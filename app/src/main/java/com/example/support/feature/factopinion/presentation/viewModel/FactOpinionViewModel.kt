package com.example.support.feature.factopinion.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseGameViewModel
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.util.GameManager
import com.example.support.core.util.HapticFeedbackManager
import com.example.support.core.util.ResultCore
import com.example.support.core.util.timer.GameTimerController
import com.example.support.core.util.timer.TimerManager
import com.example.support.feature.factopinion.model.FactOpinionEvent
import com.example.support.feature.factopinion.model.FactOpinionResult
import com.example.support.feature.factopinion.model.FactOpinionState
import com.example.support.feature.factopinion.presentation.repository.FactOpinionGameManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactOpinionViewModel @Inject constructor(
    navigator: Navigator,
    private val gameManager: FactOpinionGameManager,
    timerManager: TimerManager,
    gameTimerController: GameTimerController,
    private val vibrationManager: HapticFeedbackManager
) : BaseGameViewModel<FactOpinionState, FactOpinionEvent>(
    FactOpinionState(),
    navigator,
    timerManager,
    gameTimerController
), FactOpinionController {

    override fun onEvent(event: FactOpinionEvent) {
        when (event) {
            is FactOpinionEvent.StartGame -> {
                startGame()
            }

            is FactOpinionEvent.AnswerQuestion -> {
                checkAnswer()
            }
        }
    }

    override fun initializeGame() {
        updateState(uiState.value.copy(result = FactOpinionResult.Loading))
        viewModelScope.launch {
            when (val init = gameManager.loadShuffledIdsIfNeeded()) {
                is ResultCore.Failure -> {
                    updateState(uiState.value.copy(result = FactOpinionResult.Error(init.message)))
                    return@launch
                }
                is ResultCore.Success -> loadNextQuestion()
            }
        }
    }

    override fun selectedAnswer(buttonText: String) {
        updateState(
            uiState.value.copy(
                selectedAnswer = buttonText
            )
        )
    }

    override fun getGameManager(): GameManager = gameManager

    private fun loadNextQuestion() {
        viewModelScope.launch {
            when (val result = gameManager.getNextQuestion()) {
                is ResultCore.Success -> {
                    updateState(
                        uiState.value.copy(
                            currentQuestion = result.data.text,
                            answer = result.data.answer,
                            result = FactOpinionResult.Success,
                            selectedAnswer = ""
                        )
                    )
                }

                is ResultCore.Failure -> {
                    updateState(uiState.value.copy(result = FactOpinionResult.Error(result.message)))
                }
            }
        }
    }

    private fun checkAnswer() {
        val currentState = uiState.value
        val isCorrect = currentState.selectedAnswer == currentState.answer

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

    override fun createInitialState(): FactOpinionState = FactOpinionState()
}
