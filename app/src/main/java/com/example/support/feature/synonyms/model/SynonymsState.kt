package com.example.support.feature.synonyms.model

import com.example.support.core.ui.views.pauseDialog.model.PauseState
import com.example.support.core.util.Constants

data class SynonymsState(
    override val isPaused: Boolean = false,
    override val timer: Int = Constants.GAME_TIMER_DURATION,
    override val hasStarted: Boolean = false,
    override val score: Int = 0,
    val category: String = "",
    val mainWord: String = "",
    val selectedCount: Int = 0,
    val options: List<SynonymOption> = emptyList(),
    val correctAnswers: List<String> = emptyList(),
    val result: SynonymsResult = SynonymsResult.Success,
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