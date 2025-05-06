package com.example.support.feature.home.presentation.repository

import com.example.support.core.data.UserManager
import com.example.support.core.domain.GameModel
import com.example.support.core.domain.User
import com.example.support.core.util.ResultCore
import com.example.support.feature.seemore.presentation.repository.AllGameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val userManager: UserManager,
    private val gameRepository: AllGameRepository
) : HomeRepository {

    override fun getCurrentUser(): Flow<Result<User>> = flow {
        val result = kotlinx.coroutines.suspendCancellableCoroutine<Result<User>> { continuation ->
            userManager.getCurrentUser { result ->
                when (result) {
                    is ResultCore.Success -> continuation.resume(Result.success(result.data), null)
                    is ResultCore.Failure -> continuation.resume(Result.failure(Exception(result.message)), null)
                }
            }
        }
        emit(result)
    }

    override suspend fun getGames(): Result<List<GameModel>> {
        return gameRepository.loadGames()
    }
}