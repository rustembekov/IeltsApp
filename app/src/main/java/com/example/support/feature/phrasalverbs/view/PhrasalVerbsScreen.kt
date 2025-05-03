package com.example.support.feature.phrasalverbs.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.views.ErrorView
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsEventEvent
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsResult
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsState
import com.example.support.feature.phrasalverbs.presentation.viewModel.PhrasalVerbsController

@Composable
fun PhrasalVerbsScreen(
    modifier: Modifier = Modifier,
    state: PhrasalVerbsState,
    controller: PhrasalVerbsController
) {
    LaunchedEffect(Unit) {
        controller.onEvent(event = PhrasalVerbsEventEvent.StartGame)
    }

    when (state.result) {
        is PhrasalVerbsResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is PhrasalVerbsResult.Error -> {
            val errorMessage = (state.result as? PhrasalVerbsResult.Error)?.message ?: "Unknown error"
            ErrorView(
                message = errorMessage,
                onRetry = {
                    controller.onEvent(event = PhrasalVerbsEventEvent.StartGame)
                }
            )
        }

        else -> {
            PhrasalVerbsContentView(
                controller = controller,
                state = state,
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun PhrasalVerbsScreenPreview() {
    val mockController = object : PhrasalVerbsController {
        override fun onEvent(event: PhrasalVerbsEventEvent) {
            TODO("Not yet implemented")
        }

        override fun onInputChange(input: String) {
            TODO("Not yet implemented")
        }

    }
    AppTheme {
        PhrasalVerbsContentView(
            state = PhrasalVerbsState(),
            controller = mockController
        )
    }
}