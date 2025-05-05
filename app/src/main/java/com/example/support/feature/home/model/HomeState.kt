package com.example.support.feature.home.model

import android.net.Uri
import com.example.support.core.domain.GameModel
import com.example.support.core.domain.User

data class HomeState(
    val games: List<GameModel> = emptyList(),
    val user: User? = null,
    val result: HomeResult = HomeResult.Loading,
    val selectedImageUri: Uri? = null
)

sealed class HomeEvent {
    data object LoadUser: HomeEvent()
    data object LoadGames: HomeEvent()
    data object RefreshAvatar: HomeEvent()
}

sealed class HomeResult {
    data object Success : HomeResult()
    data object Loading : HomeResult()
    data class Error(val message: String) : HomeResult()
}

