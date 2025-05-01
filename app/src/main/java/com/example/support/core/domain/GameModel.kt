package com.example.support.core.domain

data class GameModel(
    val id: String,
    val title: String,
    val description: String,
    val imgResource: Int? = null,
    val route: String
)