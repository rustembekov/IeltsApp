package com.example.support.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.feature.login.view.LoginScreen
import com.example.support.feature.login.viewModel.LoginViewModel
import com.example.support.feature.register.view.RegisterScreen
import com.example.support.feature.register.viewModel.RegisterViewModel
import com.example.support.feature.phrasalverbs.view.PhrasalVerbsScreen
import com.example.support.feature.home.view.HomeScreen
import com.example.support.feature.profile.view.ProfileScreen
import com.example.support.feature.seemore.view.SeeMoreScreen
import com.example.support.feature.rating.view.RatingScreen
import com.example.support.core.di.NavHolder
import com.example.support.feature.essaybuilder.model.EssayBuilderState
import com.example.support.feature.essaybuilder.view.EssayBuilderScreen
import com.example.support.feature.essaybuilder.viewModel.EssayBuilderViewModel
import com.example.support.feature.factopinion.model.FactOpinionState
import com.example.support.feature.factopinion.view.FactOpinionScreen
import com.example.support.feature.factopinion.presentation.viewModel.FactOpinionViewModel
import com.example.support.feature.gamecompletion.model.GameCompletionState
import com.example.support.feature.gamecompletion.view.GameCompletionScreen
import com.example.support.feature.gamecompletion.presentation.viewModel.GameCompletionViewModel
import com.example.support.feature.home.viewModel.HomeViewModel
import com.example.support.feature.keywordscheck.model.KeywordsCheckState
import com.example.support.feature.keywordscheck.view.KeywordsCheckScreen
import com.example.support.feature.keywordscheck.presentation.viewModel.KeywordsCheckViewModel
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsState
import com.example.support.feature.phrasalverbs.presentation.viewModel.PhrasalVerbsViewModel
import com.example.support.feature.profile.model.ProfileState
import com.example.support.feature.profile.viewModel.ProfileViewModel
import com.example.support.feature.rating.presentation.viewModel.RatingViewModel
import com.example.support.feature.seemore.model.SeeMoreState
import com.example.support.feature.seemore.presentation.viewModel.SeeMoreViewModel
import com.example.support.feature.synonyms.model.SynonymsState
import com.example.support.feature.synonyms.view.SynonymsScreen
import com.example.support.feature.synonyms.presentation.viewModel.SynonymsViewModel

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationItem.Login.route,
    navigator: Navigator = hiltViewModel<NavHolder>().navigator

) {
    LaunchedEffect(Unit) {
        navigator.navigationEvents.collect { event ->
            when (event) {
                is NavigationEvent.Navigate -> {
                    navController.navigate(event.route)
                }

                is NavigationEvent.NavigateAndPopUp -> {
                    navController.popBackStack(event.popUp, inclusive = true)
                    navController.navigate(event.route)
                }

                is NavigationEvent.Back -> {
                    navController.popBackStack()
                }
            }
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val screensWithBottomNav = listOf(
        NavigationItem.Rating.route,
        NavigationItem.Home.route,
        NavigationItem.Profile.route
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        containerColor = Color.Transparent,

        bottomBar = {
            if (currentRoute in screensWithBottomNav) {
                MainNavigationTabBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = modifier.padding(paddingValues),
            navController = navController,
            startDestination = startDestination
        ) {
            composable(NavigationItem.Login.route) {
                val viewModel: LoginViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()
                LoginScreen(
                    state = state,
                    controller = viewModel,
                    navHostController = navController
                )
            }

            composable(NavigationItem.Register.route) {
                val viewModel: RegisterViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()
                RegisterScreen(
                    navHostController = navController,
                    controller = viewModel,
                    state = state
                )
            }

            composable(NavigationItem.Home.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val state by viewModel.uiState.collectAsState()
                HomeScreen(
                    controller = viewModel,
                    state = state
                )
            }

            composable(NavigationItem.FactOpinion.route) {
                val viewModel: FactOpinionViewModel = hiltViewModel()
                val state: FactOpinionState by viewModel.uiState.collectAsState()
                FactOpinionScreen(
                    controller = viewModel,
                    state = state
                )
            }

            composable(NavigationItem.PhrasalVerbs.route) {
                val viewModel: PhrasalVerbsViewModel = hiltViewModel()
                val state: PhrasalVerbsState by viewModel.uiState.collectAsState()
                PhrasalVerbsScreen(
                    controller = viewModel,
                    state = state
                )
            }

            composable(NavigationItem.KeywordsCheck.route) {
                val viewModel: KeywordsCheckViewModel = hiltViewModel()
                val state: KeywordsCheckState by viewModel.uiState.collectAsState()
                KeywordsCheckScreen(
                    controller = viewModel,
                    state = state
                )
            }

            composable(NavigationItem.Synonyms.route) {
                val viewModel: SynonymsViewModel = hiltViewModel()
                val state: SynonymsState by viewModel.uiState.collectAsState()
                SynonymsScreen(
                    controller = viewModel,
                    state = state
                )
            }

            composable(NavigationItem.EssayBuilder.route) {
                val viewModel: EssayBuilderViewModel = hiltViewModel()
                val state: EssayBuilderState by viewModel.uiState.collectAsState()
                EssayBuilderScreen(
                    controller = viewModel,
                    state = state
                )
            }

            composable(NavigationItem.Profile.route) {
                val viewModel: ProfileViewModel = hiltViewModel()
                val state: ProfileState by viewModel.uiState.collectAsState()
                ProfileScreen(
                    controller = viewModel,
                    state = state
                )
            }

            composable(NavigationItem.Rating.route) {
                val viewModel: RatingViewModel = hiltViewModel()
                val state by viewModel.uiState.collectAsState()

                RatingScreen(
                    controller = viewModel,
                    state = state
                )
            }

            composable(NavigationItem.SeeMore.route) {
                val viewModel: SeeMoreViewModel = hiltViewModel()
                val state: SeeMoreState by viewModel.uiState.collectAsState()

                SeeMoreScreen(
                    state = state,
                    controller = viewModel
                )
            }

            composable(NavigationItem.GameCompletion.route) {
                val viewModel: GameCompletionViewModel = hiltViewModel()
                val state: GameCompletionState by viewModel.uiState.collectAsState()

                GameCompletionScreen(
                    state = state,
                    controller = viewModel
                )
            }
        }
    }
}
