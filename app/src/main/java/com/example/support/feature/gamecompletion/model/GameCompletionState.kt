package com.example.support.feature.gamecompletion.model

data class GameCompletionState(
    val result: GameCompletionResult? = null,
    val previousScore: Int = 0,
    val newScore: Int = 0,
    val previousRank: Int = 0,
    val newRank: Int = 0
) {
    sealed class GameCompletionResult {
        data object Success : GameCompletionResult()
        data object Loading : GameCompletionResult()
        data class Error(val message: String) : GameCompletionResult()
    }
}

sealed class GameCompletionEvent {
    data object LoadGameState : GameCompletionEvent()
    data object NewGame : GameCompletionEvent()
    data object QuitGame : GameCompletionEvent()
}