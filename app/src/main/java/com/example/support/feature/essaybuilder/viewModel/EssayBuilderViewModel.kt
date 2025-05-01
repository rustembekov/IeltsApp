package com.example.support.feature.essaybuilder.viewModel

import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseViewModel
import com.example.support.feature.essaybuilder.model.EssayBuilderEvent
import com.example.support.feature.essaybuilder.model.EssayBuilderState
import com.example.support.feature.essaybuilder.model.EssayBuilderState.EssayBuilderResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EssayBuilderViewModel @Inject constructor(

) : BaseViewModel<EssayBuilderState, EssayBuilderEvent>(EssayBuilderState()), EssayBuilderController {

    private var timerJob: Job? = null

    override fun onEvent(event: EssayBuilderEvent) {
        when (event) {
            is EssayBuilderEvent.StartGame -> {
                startTimer()
            }
            is EssayBuilderEvent.AnswerQuestion -> {
                checkAnswerAndGoToNextQuestion()
            }
            is EssayBuilderEvent.UpdateScore -> {
                val currentState = uiState.value
                updateState (
                    currentState.copy(
                        // Score updating logic if needed
                    )
                )
            }
        }
    }

    override fun updateBlanks(blanks: List<String?>) {
        val currentState = uiState.value
        updateState(
            currentState.copy(
                currentBlanks = blanks
            )
        )
    }

    private fun startTimer() {
        stopTimer() // if already running
        timerJob = viewModelScope.launch {
            while (uiState.value.timer > 0) {
                delay(1000L)
                val currentTimer = uiState.value.timer
                if (currentTimer > 0) {
                    updateState (
                        uiState.value.copy(timer = currentTimer - 1)
                    )
                }
                if (currentTimer == 1) {
                    onGameOver()
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun checkAnswerAndGoToNextQuestion() {
        val currentState = uiState.value
        updateState (
            currentState.copy(
                isAnswerChecked = true,
                result = EssayBuilderResult.Success
            )
        )

        // TODO: Later you can add logic to load the next question here
    }

    private fun onGameOver() {
        stopTimer()
        updateState (
            uiState.value.copy(
                result = EssayBuilderResult.Error("Time is over")
            )
        )
    }
}
