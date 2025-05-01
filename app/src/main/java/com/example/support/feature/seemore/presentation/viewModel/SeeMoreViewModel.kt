package com.example.support.feature.seemore.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseViewModel
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.feature.seemore.presentation.repository.AllGameRepository
import com.example.support.feature.seemore.model.SeeMoreResult
import com.example.support.feature.seemore.model.SeeMoreState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeMoreViewModel @Inject constructor(
    private val navigator: Navigator,
    private val allGameRepository: AllGameRepository
) : BaseViewModel<SeeMoreState, SeeMoreResult>(SeeMoreState()), SeeMoreController {
    init {
        loadGames()
    }
    override fun onEvent(event: SeeMoreResult) {
        TODO("Not yet implemented")
    }

    override fun loadGames() {
        updateState(SeeMoreState(result = SeeMoreResult.Loading))

        viewModelScope.launch {
            val result = allGameRepository.loadGames()
            result.fold(
                onSuccess = { games ->
                    updateState(SeeMoreState(games = games, result = SeeMoreResult.Success))
                },
                onFailure = { error ->
                    updateState(SeeMoreState(result = SeeMoreResult.Error(error.message ?: "Unknown error")))
                }
            )
        }
    }

    override fun onNavigateBack() {
        viewModelScope.launch {
            navigator.navigate(NavigationEvent.Navigate(NavigationItem.Home.route))
        }
    }

    override fun onNavigateGame(route: String) {
        viewModelScope.launch {
            navigator.navigate(NavigationEvent.Navigate(route = route))
        }
    }

}