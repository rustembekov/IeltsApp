package com.example.support.feature.rating.model

import com.example.support.core.domain.User

data class RatingState(
    val players: List<User> = emptyList(),
    val result: RatingResult? = null
)

sealed class RatingResult {
    data object Success : RatingResult()
    data object Loading : RatingResult()
    data class Error(val message: String) : RatingResult()
}