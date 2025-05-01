package com.example.support.feature.essaybuilder.view;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.support.core.ui.AppTheme
import com.example.support.feature.essaybuilder.model.EssayBuilderEvent
import com.example.support.feature.essaybuilder.model.EssayBuilderState
import com.example.support.feature.essaybuilder.viewModel.EssayBuilderController

@Composable
fun EssayBuilderScreen(
    modifier: Modifier = Modifier,
    state: EssayBuilderState,
    controller: EssayBuilderController
) {
    when (state.result) {
        is EssayBuilderState.EssayBuilderResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is EssayBuilderState.EssayBuilderResult.Error -> {
            val errorMessage =
                (state.result as? EssayBuilderState.EssayBuilderResult.Error)?.message ?: "Unknown error"
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
            EssayBuilderContentView(
                controller = controller,
                state = state,
                modifier = modifier
            )
        }
    }
}


@Preview
@Composable
private fun EssayBuilderScreenPreview() {
    val mockController = object : EssayBuilderController {
        override fun onEvent(event: EssayBuilderEvent) {
            TODO("Not yet implemented")
        }

        override fun updateBlanks(blanks: List<String?>) {
            TODO("Not yet implemented")
        }


    }
    AppTheme {
        EssayBuilderScreen(
            state = EssayBuilderState(),
            controller = mockController
        )
    }
}