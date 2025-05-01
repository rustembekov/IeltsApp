package com.example.support.feature.gamecompletion.presentation.domain

data class GameResultDelta(
    val previousScore: Int,
    val previousRank: Int,
    val newScore: Int,
    val newRank: Int
)
