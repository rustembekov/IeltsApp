package com.example.support.feature.synonyms.presentation.data

data class SynonymsGame(
    val id: String = "",
    val category: String = "",
    val word: String = "",
    val synonyms: List<String> = emptyList(),
    val otherWords: List<String> = emptyList()
)
