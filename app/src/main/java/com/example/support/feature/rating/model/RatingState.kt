package com.example.support.feature.rating.model

import com.example.support.core.domain.User

data class RatingState(
    val players: List<User> = emptyList(),
    val avatars: Map<String, String> = emptyMap(),
    val result: RatingResult = RatingResult.Loading
)

sealed class RatingResult {
    data object Success : RatingResult()
    data object Loading : RatingResult()
    data class Error(val message: String) : RatingResult()
}