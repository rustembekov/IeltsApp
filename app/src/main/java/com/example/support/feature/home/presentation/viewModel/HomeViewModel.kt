package com.example.support.feature.home.viewModel

import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseViewModel
import com.example.support.core.domain.GameModel
import com.example.support.core.domain.User
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.feature.home.model.HomeEvent
import com.example.support.feature.home.model.HomeUiState
import com.example.support.feature.home.presentation.usecases.GetAvatarUriUseCase
import com.example.support.feature.home.presentation.usecases.GetGamesUseCase
import com.example.support.feature.home.presentation.usecases.GetUserUseCase
import com.example.support.feature.home.presentation.viewModel.HomeController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Home screen following SOLID principles.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getUserUseCase: GetUserUseCase,
    private val getGamesUseCase: GetGamesUseCase,
    private val getAvatarUriUseCase: GetAvatarUriUseCase
) : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState.Loading), HomeController {

    // Cache for data synchronization
    private var cachedUser: User? = null
    private var cachedGames: List<GameModel> = emptyList()

    init {
        // Initial data loading
        loadUser()
        loadGames()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadUser -> loadUser()
            is HomeEvent.LoadGames -> loadGames()
            is HomeEvent.RefreshAvatar -> refreshAvatar()
        }
    }

    private fun loadUser() {
        getUserUseCase()
            .onEach { result ->
                result.fold(
                    onSuccess = { user ->
                        cachedUser = user
                        combineDataIntoState()
                    },
                    onFailure = { error ->
                        updateState(HomeUiState.Error(error.message ?: "Failed to load user"))
                    }
                )
            }
            .catch { error ->
                updateState(HomeUiState.Error(error.message ?: "An unexpected error occurred"))
            }
            .launchIn(viewModelScope)
    }

    private fun loadGames() {
        viewModelScope.launch {
            try {
                val result = getGamesUseCase()
                result.fold(
                    onSuccess = { games ->
                        cachedGames = games
                        combineDataIntoState()
                    },
                    onFailure = { error ->
                        updateState(HomeUiState.Error(error.message ?: "Failed to load games"))
                    }
                )
            } catch (e: Exception) {
                updateState(HomeUiState.Error(e.message ?: "An unexpected error occurred"))
            }
        }
    }

    private fun refreshAvatar() {
        val avatarUri = getAvatarUriUseCase()
        val currentState = uiState.value

        if (currentState is HomeUiState.Content) {
            updateState(currentState.copy(selectedImageUri = avatarUri))
        } else {
            // If not in content state, just cache the avatarUri for when we do enter content state
            combineDataIntoState()
        }
    }

    /**
     * Combines cached data into a coherent UI state.
     * Only transitions to Content state when all required data is available.
     */
    private fun combineDataIntoState() {
        val user = cachedUser
        val games = cachedGames

        if (user != null && games.isNotEmpty()) {
            updateState(
                HomeUiState.Content(
                    user = user,
                    games = games,
                    selectedImageUri = getAvatarUriUseCase()
                )
            )
        }
    }

    override fun onNavigateToRoute(route: String) {
        viewModelScope.launch {
            navigator.navigate(NavigationEvent.Navigate(route))
        }
    }
}