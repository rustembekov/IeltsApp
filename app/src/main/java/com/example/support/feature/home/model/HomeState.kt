package com.example.support.feature.home.model

import android.net.Uri
import com.example.support.core.domain.GameModel
import com.example.support.core.domain.User

/**
 * Represents the UI state for the Home screen.
 * Uses sealed interface to ensure type safety and proper state handling.
 */
sealed interface HomeUiState {
    /**
     * Loading state shown before data is available
     */
    data object Loading : HomeUiState

    /**
     * Error state with descriptive message
     */
    data class Error(val message: String) : HomeUiState

    /**
     * Success state with all required data
     */
    data class Content(
        val user: User,
        val games: List<GameModel>,
        val selectedImageUri: Uri?
    ) : HomeUiState
}

/**
 * Events that can be triggered by the UI
 */
sealed interface HomeEvent {
    /**
     * Request to load or refresh user data
     */
    data object LoadUser : HomeEvent

    /**
     * Request to load or refresh games list
     */
    data object LoadGames : HomeEvent

    /**
     * Request to refresh the avatar image
     */
    data object RefreshAvatar : HomeEvent
}