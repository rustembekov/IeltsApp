package com.example.support.feature.rating.presentation.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.support.core.domain.User
import javax.inject.Inject

class AvatarRepositoryImpl @Inject constructor(
    private val context: Context
) : AvatarRepository {

    private val prefs = context.getSharedPreferences("avatar_prefs", Context.MODE_PRIVATE)

    override suspend fun getAvatarUrl(userId: String): String {
        return prefs.getString(userId, null)
            ?: "https://api.dicebear.com/7.x/thumbs/svg?seed=$userId"
    }

    override suspend fun preloadAvatarUrls(users: List<User>): Map<String, String> {
        val avatars = mutableMapOf<String, String>()
        users.forEach { user ->
            val cached = prefs.getString(user.id, null)
            val url = cached ?: "https://api.dicebear.com/7.x/thumbs/svg?seed=${user.id}".also {
                prefs.edit().putString(user.id, it).apply()
            }
            avatars[user.id] = url
        }
        return avatars
    }
}
