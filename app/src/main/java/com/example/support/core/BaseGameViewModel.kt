package com.example.support.core

import androidx.lifecycle.viewModelScope
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.ui.views.pauseDialog.model.PauseState
import kotlinx.coroutines.launch

abstract class BaseGameViewModel<S : PauseState, E : Any>(
    initialState: S,
    private val navigator: Navigator
) : BaseViewModel<S, E>(initialState) {

    open fun onPauseClicked() {
        updateState(uiState.value.copyPauseState(true) as S)
    }

    open fun onResumePauseDialog() {
        updateState(uiState.value.copyPauseState(false) as S)
    }

    fun onQuitGame() {
        viewModelScope.launch {
            navigator.navigate(NavigationEvent.Navigate(NavigationItem.Home.route))
        }
    }
}



