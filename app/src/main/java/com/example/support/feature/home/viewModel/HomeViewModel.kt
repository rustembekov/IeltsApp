package com.example.support.feature.home.viewModel

import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseViewModel
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.util.AvatarManager
import com.example.support.core.util.ResultCore
import com.example.support.feature.seemore.presentation.repository.AllGameRepository
import com.example.support.core.data.UserRepository
import com.example.support.feature.home.model.HomeEvent
import com.example.support.feature.home.model.HomeResult
import com.example.support.feature.home.model.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val allGameRepository: AllGameRepository,
    private val userRepository: UserRepository,
    private val avatarManager: AvatarManager

) : BaseViewModel<HomeState, HomeEvent>(HomeState()), HomeController {

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadUser -> {
                loadUser()
            }

            is HomeEvent.RefreshAvatar -> {
                refreshAvatar()
            }

            is HomeEvent.LoadGames -> {
                loadGames()
            }
        }
    }

    private fun refreshAvatar() {
        updateState(
            uiState.value.copy(
                selectedImageUri = avatarManager.getAvatarUri()
            )
        )
    }

    private fun loadUser() {
        updateState(uiState.value.copy(result = HomeResult.Loading))

        userRepository.getCurrentUser { result ->
            when (result) {
                is ResultCore.Success -> {
                    val selectedAvatar = avatarManager.getAvatarUri()
                    val latestState = uiState.value

                    updateState(
                        latestState.copy(
                            user = result.data,
                            selectedImageUri = selectedAvatar,
                            result = HomeResult.Success
                        )
                    )
                }

                is ResultCore.Failure -> {
                    updateState(
                        uiState.value.copy(
                            result = HomeResult.Error(result.message)
                        )
                    )
                }
            }
        }
    }

    private fun loadGames() {
        updateState(HomeState(result = HomeResult.Loading))

        viewModelScope.launch {
            val result = allGameRepository.loadGames()
            result.fold(
                onSuccess = { games ->
                    updateState(uiState.value.copy(games = games, result = HomeResult.Success))
                },
                onFailure = { error ->
                    updateState(
                        uiState.value.copy(
                            result = HomeResult.Error(
                                error.message ?: "Unknown error"
                            )
                        )
                    )
                }
            )
        }
    }

    override fun onNavigateToRoute(route: String) {
        viewModelScope.launch {
            navigator.navigate(NavigationEvent.Navigate(route))
        }
    }
}
