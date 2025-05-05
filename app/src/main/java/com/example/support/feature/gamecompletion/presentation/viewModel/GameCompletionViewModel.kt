package com.example.support.feature.gamecompletion.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseViewModel
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.util.ResultCore
import com.example.support.feature.gamecompletion.model.GameCompletionEvent
import com.example.support.feature.gamecompletion.model.GameCompletionState
import com.example.support.feature.gamecompletion.presentation.repository.GameCompletionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameCompletionViewModel @Inject constructor(
    private val gameManager: GameCompletionManager,
    private val navigator: Navigator
) : BaseViewModel<GameCompletionState, GameCompletionEvent>((GameCompletionState())),
    GameCompletionController {
    override fun onEvent(event: GameCompletionEvent) {
        when (event) {
            is GameCompletionEvent.LoadGameState -> {
                loadGameState()
            }

            is GameCompletionEvent.NewGame -> {
                restartGame()
            }
            is GameCompletionEvent.QuitGame -> {
                viewModelScope.launch {
                    navigator.navigate(NavigationEvent.Navigate(NavigationItem.Home.route))
                }
            }
        }
    }

    private fun restartGame() {
        viewModelScope.launch {
            val previousGameRoute = gameManager.getPreviousGameRoute()


            if (!previousGameRoute.isNullOrEmpty()) {
                navigator.navigate(NavigationEvent.Navigate(previousGameRoute))
            } else {
                navigator.navigate(NavigationEvent.Back)
            }
        }
    }

    private fun loadGameState() {
        updateState(uiState.value.copy(result = GameCompletionState.GameCompletionResult.Loading))
        viewModelScope.launch {
            when (val res = gameManager.completeGame()) {
                is ResultCore.Success -> updateState(
                    uiState.value.copy(
                        newRank = res.data.newRank,
                        newScore = res.data.newScore,
                        previousRank = res.data.previousRank,
                        previousScore = res.data.previousScore,
                        result = GameCompletionState.GameCompletionResult.Success
                    )
                )

                is ResultCore.Failure -> {
                    updateState(
                        uiState.value.copy(
                            result = GameCompletionState.GameCompletionResult.Error(
                                "Error to load data!"
                            )
                        )
                    )

                }
            }
        }
    }
}