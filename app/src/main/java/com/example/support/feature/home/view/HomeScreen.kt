package com.example.support.feature.home.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.support.core.ui.AppTheme
import com.example.support.core.domain.GameModel
import com.example.support.feature.home.model.HomeEvent
import com.example.support.feature.home.model.HomeResult
import com.example.support.feature.home.model.HomeState
import com.example.support.feature.home.viewModel.HomeController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    controller: HomeController,
    state: HomeState?
) {
    LaunchedEffect(Unit) {
        controller.onEvent(HomeEvent.RefreshAvatar)
        controller.onEvent(HomeEvent.LoadGames)
        controller.onEvent(HomeEvent.LoadUser)
    }
    Log.d("HomeScreen", "User data: " + state?.user.toString())
    when {
        state == null || state.result is HomeResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.result is HomeResult.Error -> {
            val errorMessage = (state.result as? HomeResult.Error)?.message ?: "Unknown error"
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
            HomeContentView(
                controller = controller,
                state = state,
                modifier = modifier
            )
        }
    }
}

private val mockGames =  listOf(
    GameModel(
        id = "1",
        title = "First Game",
        description = "Test your knowledge",
        route = com.example.support.core.navigation.model.NavigationItem.FactOpinion.route
    ),
    GameModel(
        id = "2",
        title = "Second Game",
        description = "Challenge yourself",
        route = com.example.support.core.navigation.model.NavigationItem.PhrasalVerbs.route
    ),
    GameModel(
        id = "3",
        title = "Third Game",
        description = "Improve your skills",
        route = com.example.support.core.navigation.model.NavigationItem.KeywordsCheck.route
    )
)

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme(
        darkTheme = false
    ) {
        val mockController = object: HomeController {
            override fun onNavigateToRoute(route: String) {
                TODO("Not yet implemented")
            }

            override fun onEvent(event: HomeEvent) {
                TODO("Not yet implemented")
            }

        }
        HomeScreen(
            controller = mockController,
            state = HomeState(
                games = mockGames
            )
        )
    }
}
