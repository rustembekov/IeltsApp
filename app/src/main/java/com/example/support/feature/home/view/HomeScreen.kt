package com.example.support.feature.home.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.support.core.ui.AppTheme
import com.example.support.core.domain.GameModel
import com.example.support.core.domain.User
import com.example.support.core.ui.views.ErrorView
import com.example.support.feature.home.model.HomeEvent
import com.example.support.feature.home.model.HomeUiState
import com.example.support.feature.home.presentation.viewModel.HomeController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    controller: HomeController,
    state: HomeUiState
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                controller.onEvent(HomeEvent.LoadUser)
                controller.onEvent(HomeEvent.RefreshAvatar)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when (state) {
        is HomeUiState.Loading -> LoadingScreen()
        is HomeUiState.Error -> ErrorScreen(state.message) {
            controller.onEvent(HomeEvent.LoadUser)
            controller.onEvent(HomeEvent.LoadGames)
        }
        is HomeUiState.Content -> ContentScreen(state, controller, modifier)
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    ErrorView(
        message = message,
        onRetry = onRetry
    )
}

@Composable
private fun ContentScreen(
    state: HomeUiState.Content,
    controller: HomeController,
    modifier: Modifier = Modifier
) {
    // Your existing HomeContentView implementation
    // Adapt it to use state.user, state.games, etc.
    HomeContentView(
        state = state,
        controller = controller,
        modifier = modifier
    )
}

private val mockGames = listOf(
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
private fun HomeScreenContentPreview() {
    AppTheme(darkTheme = false) {
        val mockController = object : HomeController {
            override fun onNavigateToRoute(route: String) {}
            override fun onEvent(event: HomeEvent) {}
        }

        HomeScreen(
            controller = mockController,
            state = HomeUiState.Content(
                user = User(),
                games = mockGames,
                selectedImageUri = null
            )
        )
    }
}

@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    AppTheme(darkTheme = false) {
        val mockController = object : HomeController {
            override fun onNavigateToRoute(route: String) {}
            override fun onEvent(event: HomeEvent) {}
        }

        HomeScreen(
            controller = mockController,
            state = HomeUiState.Loading
        )
    }
}

@Preview
@Composable
private fun HomeScreenErrorPreview() {
    AppTheme(darkTheme = false) {
        val mockController = object : HomeController {
            override fun onNavigateToRoute(route: String) {}
            override fun onEvent(event: HomeEvent) {}
        }

        HomeScreen(
            controller = mockController,
            state = HomeUiState.Error("Something went wrong")
        )
    }
}