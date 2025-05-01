package com.example.support.feature.synonyms.model

import com.example.support.core.ui.views.pauseDialog.model.PauseState
import com.example.support.core.util.Constants

data class SynonymsState(
    val category: String = "",
    val timer: Int = Constants.GAME_TIMER_DURATION,
    val mainWord: String = "",
    val hasStarted: Boolean = false,
    val score: Int = 0,
    val selectedCount: Int = 0,
    val options: List<SynonymOption> = emptyList(),
    val correctAnswers: List<String> = emptyList(),
    val result: SynonymsResult = SynonymsResult.Success,
    override val isPauseDialogVisible: Boolean = false
): PauseState {
    override fun copyPauseState(isPauseDialogVisible: Boolean): SynonymsState {
        return copy(isPauseDialogVisible = isPauseDialogVisible)
    }
}

data class SynonymOption(
    val text: String,
    val isSelected: Boolean = false,
    val isCorrect: Boolean? = null
)

sealed class SynonymsResult {
    data object Success : SynonymsResult()
    data object Loading : SynonymsResult()
    data class Error(val message: String) : SynonymsResult()
}

sealed class SynonymsEvent {
    data object StartGame : SynonymsEvent()
    data object AnswerQuestion : SynonymsEvent()
}