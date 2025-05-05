package com.example.support.feature.essaybuilder.model

import com.example.support.core.ui.views.pauseDialog.model.PauseState
import com.example.support.core.util.Constants

data class EssayBuilderState(
    override val isPaused: Boolean = false,
    override val timer: Int = Constants.GAME_TIMER_DURATION,
    override val hasStarted: Boolean = false,
    override val score: Int = 0,
    val questionParts: List<Part> = emptyList(),
    val options: List<OptionUiModel> = emptyList(),
    val currentBlanks: List<BlanksUiModel?> = emptyList(),
    val correctAnswers: List<String> = emptyList(),
    val isAnswerChecked: Boolean = false,
    val tokens: List<Token> = emptyList(),
    val result: EssayBuilderResult? = null,
    val isShownCorrectAnswer: Boolean = false,
    val currentlyDraggedWord: String? = null,
    val selectedWord: String? = null,
): PauseState {
    sealed class Part {
        data class Text(val text: String) : Part()
        data class Blank(val index: Int) : Part()
    }

    data class Token(
        val content: String = "",
        val isBlank: Boolean = false,
        val isSpace: Boolean = false,
        val blankIndex: Int = -1
    )

    data class OptionUiModel(
        val word: String,
        val isSelected: Boolean = false,
        val isUsed: Boolean = false
    )

    data class BlanksUiModel(
        val word: String,
        val isSelected: Boolean = true,
        val isCorrect: Boolean = false
    )

    sealed class EssayBuilderResult {
        data object Success : EssayBuilderResult()
        data object Loading : EssayBuilderResult()
        data class Error(val message: String) : EssayBuilderResult()
    }

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

sealed class EssayBuilderEvent {
    data object StartGame : EssayBuilderEvent()
    data object AnswerQuestion : EssayBuilderEvent()
}




