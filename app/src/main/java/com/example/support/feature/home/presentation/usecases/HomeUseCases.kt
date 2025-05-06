package com.example.support.feature.home.presentation.usecases

import android.net.Uri
import com.example.support.core.domain.GameModel
import com.example.support.core.domain.User
import com.example.support.feature.home.presentation.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get the current user.
 * Following the Single Responsibility Principle.
 */
class GetUserUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(): Flow<Result<User>> {
        return repository.getCurrentUser()
    }
}

/**
 * Use case to get available games.
 * Following the Single Responsibility Principle.
 */
class GetGamesUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<GameModel>> {
        return repository.getGames()
    }
}

/**
 * Use case to get the user's avatar URI.
 * Following the Single Responsibility Principle.
 */
interface GetAvatarUriUseCase {
    operator fun invoke(): Uri?
}

/**
 * Implementation of GetAvatarUriUseCase.
 * Decouples the ViewModel from direct Avatar management.
 */
class GetAvatarUriUseCaseImpl @Inject constructor(
    private val avatarManager: com.example.support.core.util.AvatarManager
) : GetAvatarUriUseCase {
    override operator fun invoke(): Uri? {
        return avatarManager.getAvatarUri()
    }
}