package com.example.support.feature.home.presentation.viewModel

import com.example.support.feature.home.model.HomeEvent

interface HomeController {
    fun onNavigateToRoute(route: String)
    fun onEvent(event: HomeEvent)
}