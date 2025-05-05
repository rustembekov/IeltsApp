package com.example.support.feature.keywordscheck.view

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
import com.example.support.feature.keywordscheck.model.KeywordsCheckEvent
import com.example.support.feature.keywordscheck.model.KeywordsCheckResult
import com.example.support.feature.keywordscheck.model.KeywordsCheckState
import com.example.support.feature.keywordscheck.presentation.viewModel.KeywordsCheckController

@Composable
fun KeywordsCheckScreen(
    modifier: Modifier = Modifier,
    state: KeywordsCheckState,
    controller: KeywordsCheckController
) {
    LaunchedEffect(Unit) {
        controller.onEvent(KeywordsCheckEvent.StartGame)
    }

    when (state.result) {
        is KeywordsCheckResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is KeywordsCheckResult.Error -> {
            val errorMessage =
                (state.result as? KeywordsCheckResult.Error)?.message ?: "Unknown error"
            ErrorView(
                message = errorMessage,
                onRetry = {
                    controller.onEvent(KeywordsCheckEvent.StartGame)
                }
            )
        }

        else -> {
            KeywordsCheckContentView(
                controller = controller,
                state = state,
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun KeywordsCheckScreenPreview() {
    AppTheme(
        darkTheme = false
    ) {
        val mockController = object : KeywordsCheckController {
            override fun onEvent(event: KeywordsCheckEvent) {
                TODO("Not yet implemented")
            }

            override fun toggleWordSelection(word: String) {
                TODO("Not yet implemented")
            }

        }
        KeywordsCheckScreen(
            state = KeywordsCheckState(
                result = KeywordsCheckResult.Success
            ),
            controller = mockController
        )
    }
}