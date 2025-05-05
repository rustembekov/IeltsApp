package com.example.support.feature.factopinion.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.views.ErrorView
import com.example.support.feature.factopinion.model.FactOpinionEvent
import com.example.support.feature.factopinion.model.FactOpinionResult
import com.example.support.feature.factopinion.model.FactOpinionState
import com.example.support.feature.factopinion.presentation.viewModel.FactOpinionController

@Composable
fun FactOpinionScreen(
    modifier: Modifier = Modifier,
    controller: FactOpinionController,
    state: FactOpinionState
) {

    LaunchedEffect(Unit) {
        controller.onEvent(FactOpinionEvent.StartGame)
    }

    when (state.result) {
        is FactOpinionResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is FactOpinionResult.Error -> {
            val errorMessage = (state.result as? FactOpinionResult.Error)?.message ?: "Unknown error"
            ErrorView(
                message = errorMessage,
                onRetry = {
                    controller.onEvent(event = FactOpinionEvent.StartGame)
                }
            )
        }

        else -> {
            FactOpinionContentView(
                controller = controller,
                state = state,
                modifier = modifier
            )
        }
    }

}

@Preview
@Composable
private fun FactOpinionScreenPreview() {
    val mockController = object: FactOpinionController {
        override fun onEvent(event: FactOpinionEvent) {
            TODO("Not yet implemented")
        }

        override fun selectedAnswer(buttonText: String) {
            TODO("Not yet implemented")
        }

    }

    AppTheme {
        FactOpinionScreen(
            controller = mockController,
            state = FactOpinionState(
                currentQuestion = "Test Question"
            ),
        )
    }
}