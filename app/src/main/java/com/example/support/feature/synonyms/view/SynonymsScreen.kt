package com.example.support.feature.synonyms.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.support.core.ui.AppTheme
import com.example.support.feature.factopinion.model.FactOpinionEvent
import com.example.support.feature.synonyms.model.SynonymsEvent
import com.example.support.feature.synonyms.model.SynonymsResult
import com.example.support.feature.synonyms.model.SynonymsState
import com.example.support.feature.synonyms.presentation.viewModel.SynonymsController

@Composable
fun SynonymsScreen(
    modifier: Modifier = Modifier,
    controller: SynonymsController,
    state: SynonymsState
) {
    LaunchedEffect(Unit) {
        controller.onEvent(SynonymsEvent.StartGame)
    }

    when (state.result) {
        is SynonymsResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is SynonymsResult.Error -> {
            val errorMessage = (state.result as? SynonymsResult.Error)?.message ?: "Unknown error"
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: $errorMessage",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        else -> {
            SynonymsContentView(
                controller = controller,
                state = state,
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun SynonymsScreenPreview() {
    val mockController = object: SynonymsController {
        override fun onEvent(event: SynonymsEvent) {
            TODO("Not yet implemented")
        }

        override fun toggleSelection(index: Int) {
            TODO("Not yet implemented")
        }


    }
    AppTheme {
        SynonymsScreen(
            state = SynonymsState(
                result = SynonymsResult.Success
            ),
            controller = mockController
        )
    }
}