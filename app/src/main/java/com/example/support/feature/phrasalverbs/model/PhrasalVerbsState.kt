package com.example.support.feature.phrasalverbs.model

import com.example.support.core.ui.views.pauseDialog.model.PauseState

data class PhrasalVerbsState(
    val currentQuestion: String = "",
    val score: Int = 0,
    val answer: String = "",
    val timer: Int = 30,
    val userInput: String = "",
    val result: PhrasalVerbsResult = PhrasalVerbsResult.Loading,
    val phrasalVerbsEventEvent: PhrasalVerbsEventEvent? = null,
    val hasStarted: Boolean = false,
    val isShownCorrectAnswer: Boolean = false,
    override val isPauseDialogVisible: Boolean = false
): PauseState {
    override fun copyPauseState(isPauseDialogVisible: Boolean): PauseState {
        return copy(isPauseDialogVisible = isPauseDialogVisible)
    }
}

sealed class PhrasalVerbsResult {
    data object Success : PhrasalVerbsResult()
    data object Loading : PhrasalVerbsResult()
    data class Error(val message: String) : PhrasalVerbsResult()
}

sealed class PhrasalVerbsEventEvent {
    data object StartGame : PhrasalVerbsEventEvent()
    data object Answer : PhrasalVerbsEventEvent()
}
