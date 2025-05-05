package com.example.support.feature.gamecompletion.presentation.domain

data class GameCompletionData(
    val previousScore: Int,
    val previousRank: Int,
    val newScore: Int,
    val newRank: Int
)
