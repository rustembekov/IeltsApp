package com.example.support.feature.essaybuilder.presentation.data

import java.util.UUID

data class EssayBuilderGame(
    val id: String = UUID.randomUUID().toString(),
    var questionParts: List<QuestionPart> = emptyList(),
    var options: List<String> = emptyList(),
    var currentBlanks: List<String> = emptyList(),
    var correctAnswers: List<String> = emptyList()
)

data class QuestionPart(
    var type: String = "",
    var value: String? = null, // for text
    var index: Int? = null     // for blank
)

