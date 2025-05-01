package com.example.support.feature.seemore.model

import com.example.support.core.domain.GameModel

data class SeeMoreState(
    val games: List<GameModel> = emptyList(),
    val result: SeeMoreResult? = null
)

sealed class SeeMoreResult {
    data object Success : SeeMoreResult()
    data object Loading : SeeMoreResult()
    data class Error(val message: String) : SeeMoreResult()
}