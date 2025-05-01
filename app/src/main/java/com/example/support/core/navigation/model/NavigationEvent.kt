package com.example.support.core.navigation.model

sealed class NavigationEvent {
    data class Navigate(val route: String) : NavigationEvent()
    data class NavigateAndPopUp(val route: String, val popUp: String) : NavigationEvent()
    data object Back : NavigationEvent()
}
