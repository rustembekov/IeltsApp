package com.example.support.feature.home.presentation.repository

import com.example.support.core.domain.GameModel
import com.example.support.core.domain.User
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getCurrentUser(): Flow<Result<User>>

    suspend fun getGames(): Result<List<GameModel>>
}