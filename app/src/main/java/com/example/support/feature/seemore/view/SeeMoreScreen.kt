package com.example.support.feature.seemore.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.core.domain.GameModel
import com.example.support.core.ui.views.ErrorView
import com.example.support.feature.seemore.model.SeeMoreResult
import com.example.support.feature.seemore.model.SeeMoreState
import com.example.support.feature.seemore.presentation.viewModel.SeeMoreController

@Composable
fun SeeMoreScreen(
    modifier: Modifier = Modifier,
    controller: SeeMoreController,
    state: SeeMoreState,
) {
    when (state.result) {
        is SeeMoreResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is SeeMoreResult.Error -> {
            val errorMessage = state.result.message
            ErrorView(
                message = errorMessage,
                onRetry = { controller.loadGames() }
            )
        }

        else -> {
            SeeMoreContentView(
                controller = controller,
                state = state,
                modifier = modifier
            )
        }
    }
}


private val mockGames = listOf(
    GameModel(
        id = "1",
        title = "Fact or Opinion",
        description = "Test your knowledge",
        imgResource = R.drawable.img_phrasal_verb,
        route = com.example.support.core.navigation.model.NavigationItem.FactOpinion.route
    ),
    GameModel(
        id = "2",
        title = "Phrasal Verb",
        description = "Challenge yourself",
        imgResource = R.drawable.img_fact_opinion,
        route = com.example.support.core.navigation.model.NavigationItem.PhrasalVerbs.route
    ),
    GameModel(
        id = "3",
        title = "Third Game",
        description = "Improve your skills",
        imgResource = R.drawable.img_phrasal_verb,
        route = com.example.support.core.navigation.model.NavigationItem.KeywordsCheck.route
    )
)

@Preview
@Composable
private fun SeeMoreScreenPreview() {
    val mockController = object : SeeMoreController {
        override fun loadGames() {
            TODO("Not yet implemented")
        }

        override fun onNavigateBack() {
            TODO("Not yet implemented")
        }

        override fun onNavigateGame(route: String) {
            TODO("Not yet implemented")
        }
    }

    AppTheme {
        SeeMoreScreen(
            state = SeeMoreState(
                games = mockGames
            ),
            controller = mockController
        )
    }
}