package com.example.support.core.ui.views.pauseDialog.model

interface PauseState {
    val isPaused: Boolean
    val timer: Int
    val hasStarted: Boolean
    val score: Int

    /**
     * Updates the pause state
     */
    fun copyPauseState(isPaused: Boolean): PauseState

    /**
     * Updates the timer value
     */
    fun copyWithTimer(timer: Int): PauseState

    /**
     * Updates the game started state
     */
    fun copyWithGameStarted(hasStarted: Boolean): PauseState
}