package com.example.support.feature.essaybuilder.model


data class EssayBuilderState(
    val questionParts: List<Part> = emptyList(),
    val options: List<String> = emptyList(),
    val currentBlanks: List<String?> = emptyList(),
    val timer: Int = 60,
    val isAnswerChecked: Boolean = false,
    val result: EssayBuilderResult? = null
){
    sealed class Part {
        data class Text(val text: String) : Part()
        data class Blank(val index: Int) : Part()
    }

    sealed class EssayBuilderResult {
        data object Success : EssayBuilderResult()
        data object Loading : EssayBuilderResult()
        data class Error(val message: String) : EssayBuilderResult()
    }
}

sealed class EssayBuilderEvent {
    data object StartGame : EssayBuilderEvent()
    data object AnswerQuestion : EssayBuilderEvent()
    data class UpdateScore(val points: Int) : EssayBuilderEvent()
}




