package com.example.support.feature.seemore.presentation.viewModel

interface SeeMoreController {
    fun loadGames()
    fun onNavigateBack()
    fun onNavigateGame(route: String)
}