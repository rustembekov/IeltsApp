package com.example.support.core.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamePreferencesImpl @Inject constructor(
    @ApplicationContext context: Context
) : GamePreferences {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        "game_preferences",
        Context.MODE_PRIVATE
    )

    override fun getLastPlayedGame(): String {
        return prefs.getString(KEY_LAST_PLAYED_GAME, "") ?: ""
    }

    override fun setLastPlayedGame(gameType: String) {
        prefs.edit().putString(KEY_LAST_PLAYED_GAME, gameType).apply()
    }

    companion object {
        private const val KEY_LAST_PLAYED_GAME = "last_played_game"
    }
}