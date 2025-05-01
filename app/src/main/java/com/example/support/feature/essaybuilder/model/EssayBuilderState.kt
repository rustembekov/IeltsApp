package com.example.support.feature.essaybuilder.model

import com.example.support.core.ui.views.pauseDialog.model.PauseState

data class EssayBuilderState(
    val score: Int = 0,
    val questionParts: List<Part> = emptyList(),
    val options: List<OptionUiModel> = emptyList(),
    val currentBlanks: List<BlanksUiModel?> = emptyList(),
    val correctAnswers: List<String> = emptyList(),
    val timer: Int = 30,
    val isAnswerChecked: Boolean = false,
    val result: EssayBuilderResult? = null,
    val hasStarted: Boolean = false,
    val isShownCorrectAnswer: Boolean = false,
    val currentlyDraggedWord: String? = null,
    val selectedWord: String? = null,

    override val isPauseDialogVisible: Boolean = false
): PauseState{
    sealed class Part {
        data class Text(val text: String) : Part()
        data class Blank(val index: Int) : Part()
    }

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

    override fun copyPauseState(isPauseDialogVisible: Boolean): PauseState {
        return copy(isPauseDialogVisible = isPauseDialogVisible)
    }
}

sealed class EssayBuilderEvent {
    data object StartGame : EssayBuilderEvent()
    data object AnswerQuestion : EssayBuilderEvent()
}




