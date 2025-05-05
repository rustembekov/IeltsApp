package com.example.support.feature.rating.presentation.usecase

import com.example.support.core.domain.User
import com.example.support.feature.rating.presentation.repository.AvatarRepository
import javax.inject.Inject

class FetchAvatarsUseCase @Inject constructor(
    private val avatarRepository: AvatarRepository
) {
    suspend fun execute(users: List<User>): Map<String, String> {
        return avatarRepository.preloadAvatarUrls(users)
    }
}
