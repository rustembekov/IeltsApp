package com.example.support.feature.keywordscheck.model

import com.example.support.core.ui.views.pauseDialog.model.PauseState
import com.example.support.core.util.Constants

data class KeywordsCheckState(
    val currentQuestion: String = "",
    val correctAnswers: List<String> = emptyList(),
    val score: Int = 0,
    val selectedWords: List<KeywordWord> = emptyList(),
    val timer: Int = Constants.GAME_TIMER_DURATION,
    val hasStarted: Boolean = false,
    val result: KeywordsCheckResult = KeywordsCheckResult.Loading,
    val keywordsCheckEvent: KeywordsCheckEvent? = null,
    override val isPauseDialogVisible: Boolean = false
): PauseState {
    override fun copyPauseState(isPauseDialogVisible: Boolean): KeywordsCheckState {
        return copy(isPauseDialogVisible = isPauseDialogVisible)
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