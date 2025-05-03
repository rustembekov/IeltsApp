package com.example.support.feature.rating.presentation.repository

import com.example.support.core.domain.User

interface AvatarRepository {
    suspend fun getAvatarUrl(userId: String): String
    suspend fun preloadAvatarUrls(users: List<User>): Map<String, String>
}
