package com.example.support.feature.phrasalverbs.model

import com.example.support.core.ui.views.pauseDialog.model.PauseState
import com.example.support.core.util.Constants

data class PhrasalVerbsState(
    override val isPaused: Boolean = false,
    override val timer: Int = Constants.GAME_TIMER_DURATION,
    override val hasStarted: Boolean = false,
    override val score: Int = 0,
    val currentQuestion: String = "",
    val answer: String = "",
    val userInput: String = "",
    val result: PhrasalVerbsResult = PhrasalVerbsResult.Loading,
    val phrasalVerbsEventEvent: PhrasalVerbsEventEvent? = null,
    val isShownCorrectAnswer: Boolean = false,
): PauseState {
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

sealed class PhrasalVerbsResult {
    data object Success : PhrasalVerbsResult()
    data object Loading : PhrasalVerbsResult()
    data class Error(val message: String) : PhrasalVerbsResult()
}

sealed class PhrasalVerbsEventEvent {
    data object StartGame : PhrasalVerbsEventEvent()
    data object Answer : PhrasalVerbsEventEvent()
}
