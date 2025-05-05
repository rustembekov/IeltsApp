package com.example.support.feature.keywordscheck.model

import com.example.support.core.ui.views.pauseDialog.model.PauseState
import com.example.support.core.util.Constants

data class KeywordsCheckState(
    override val isPaused: Boolean = false,
    override val timer: Int = Constants.GAME_TIMER_DURATION,
    override val hasStarted: Boolean = false,
    override val score: Int = 0,
    val currentQuestion: String = "",
    val correctAnswers: List<String> = emptyList(),
    val selectedWords: List<KeywordWord> = emptyList(),
    val result: KeywordsCheckResult = KeywordsCheckResult.Loading,
    val maxSelectableWords: Int = 0,
    val keywordsCheckEvent: KeywordsCheckEvent? = null,
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

sealed class KeywordsCheckResult {
    data object Success : KeywordsCheckResult()
    data object Loading : KeywordsCheckResult()
    data class Error(val message: String) : KeywordsCheckResult()
}

data class KeywordWord(
    val text: String,
    var isSelected: Boolean = false,
    var isCorrect: Boolean? = null
)


sealed class KeywordsCheckEvent {
    data object StartGame : KeywordsCheckEvent()
    data object AnswerQuestion : KeywordsCheckEvent()
}