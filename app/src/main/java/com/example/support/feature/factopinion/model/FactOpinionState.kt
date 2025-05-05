package com.example.support.feature.factopinion.model

import com.example.support.core.ui.views.pauseDialog.model.PauseState
import com.example.support.core.util.Constants

data class FactOpinionState(
    override val isPaused: Boolean = false,
    override val timer: Int = Constants.GAME_TIMER_DURATION,
    override val hasStarted: Boolean = false,
    override val score: Int = 0,
    val currentQuestion: String = "",
    val answer: String = "",
    val selectedAnswer: String = "",
    val result: FactOpinionResult? = null,
    val isShownCorrectAnswer: Boolean = false,
) : PauseState {
    override fun copyPauseState(isPaused: Boolean): PauseState {
        return this.copy(isPaused = isPaused)
    }

    override fun copyWithTimer(timer: Int): PauseState {
        return this.copy(timer = timer)
    }

    override fun copyWithGameStarted(hasStarted: Boolean): PauseState {
        return this.copy(hasStarted = hasStarted)
    }
}

sealed class FactOpinionEvent {
    data object StartGame : FactOpinionEvent()
    data object AnswerQuestion : FactOpinionEvent()
}

sealed class FactOpinionResult {
    data object Success : FactOpinionResult()
    data object Loading : FactOpinionResult()
    data class Error(val message: String) : FactOpinionResult()
}