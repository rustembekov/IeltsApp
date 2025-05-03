package com.example.support.feature.rating.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.views.ErrorView
import com.example.support.feature.rating.model.RatingResult
import com.example.support.feature.rating.model.RatingState
import com.example.support.feature.rating.presentation.viewModel.RatingController

@Composable
fun RatingScreen(
    modifier: Modifier = Modifier,
    state: RatingState?,
    controller: RatingController
) {
    when {
        state == null || state.result is RatingResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.result is RatingResult.Error -> {
            val errorMessage = state.result.message
            ErrorView(
                message = errorMessage,
                onRetry = {controller.loadUsers()})
        }

        else -> {
            RatingContentView(
                controller = controller,
                state = state,
                modifier = modifier
            )
        }
    }

}


@Preview
@Composable
private fun RatingScreenPreview() {
    val mockController = object : RatingController {
        override fun loadUsers() {
            TODO("Not yet implemented")
        }

    }
    AppTheme(
        darkTheme = false
    ) {
        RatingScreen(
            state = RatingState(
                result = RatingResult.Success
            ),
            controller = mockController
        )
    }
}