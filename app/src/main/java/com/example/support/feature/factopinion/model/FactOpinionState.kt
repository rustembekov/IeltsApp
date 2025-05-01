package com.example.support.feature.factopinion.model

import com.example.support.core.ui.views.pauseDialog.model.PauseState
import com.example.support.core.util.Constants

data class FactOpinionState(
    val score: Int = 0,
    val currentQuestion: String = "",
    val answer: String = "",
    val selectedAnswer: String = "",
    val timer: Int = Constants.GAME_TIMER_DURATION,
    val result: FactOpinionResult? = null,
    val hasStarted: Boolean = false,
    val isShownCorrectAnswer: Boolean = false,
    override val isPauseDialogVisible: Boolean = false
) : PauseState {
    override fun copyPauseState(isPauseDialogVisible: Boolean): FactOpinionState {
        return copy(isPauseDialogVisible = isPauseDialogVisible)
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