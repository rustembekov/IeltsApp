package com.example.support.feature.gamecompletion.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.views.ErrorView
import com.example.support.feature.gamecompletion.model.GameCompletionEvent
import com.example.support.feature.gamecompletion.model.GameCompletionState
import com.example.support.feature.gamecompletion.presentation.viewModel.GameCompletionController

@Composable
fun GameCompletionScreen(
    modifier: Modifier = Modifier,
    state: GameCompletionState,
    controller: GameCompletionController
) {
    LaunchedEffect(Unit) {
        controller.onEvent(event = GameCompletionEvent.LoadGameState)
    }

    when (state.result) {
        is GameCompletionState.GameCompletionResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is GameCompletionState.GameCompletionResult.Error -> {
            val errorMessage = (state.result as? GameCompletionState.GameCompletionResult.Error)?.message ?: "Unknown error"

            ErrorView(
                message = errorMessage,
                onRetry = {
                    controller.onEvent(event = GameCompletionEvent.LoadGameState)
                }
            )
        }

        else -> {
            GameCompletionContentView(
                controller = controller,
                state = state,
                modifier = modifier
            )
        }
    }

}

@Preview
@Composable
private fun GameCompletionScreenPreview() {
    val mockController = object: GameCompletionController {
        override fun onEvent(event: GameCompletionEvent) {
            TODO("Not yet implemented")
        }
    }
    AppTheme {
        GameCompletionScreen(
            state = GameCompletionState(),
            controller = mockController
        )
    }
}