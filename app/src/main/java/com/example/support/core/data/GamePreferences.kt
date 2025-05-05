package com.example.support.core.data

interface GamePreferences {
    fun getLastPlayedGame(): String
    fun setLastPlayedGame(gameType: String)
}