package com.example.support.feature.home.presentation.di

import com.example.support.core.data.UserManager
import com.example.support.core.util.AvatarManager
import com.example.support.feature.home.presentation.repository.HomeRepository
import com.example.support.feature.home.presentation.repository.HomeRepositoryImpl
import com.example.support.feature.home.presentation.usecases.GetAvatarUriUseCase
import com.example.support.feature.home.presentation.usecases.GetAvatarUriUseCaseImpl
import com.example.support.feature.home.presentation.usecases.GetGamesUseCase
import com.example.support.feature.home.presentation.usecases.GetUserUseCase
import com.example.support.feature.seemore.presentation.repository.AllGameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeRepository(
        userManager: UserManager,
        gameRepository: AllGameRepository
    ): HomeRepository {
        return HomeRepositoryImpl(userManager, gameRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(repository: HomeRepository): GetUserUseCase {
        return GetUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetGamesUseCase(repository: HomeRepository): GetGamesUseCase {
        return GetGamesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAvatarUriUseCase(avatarManager: AvatarManager): GetAvatarUriUseCase {
        return GetAvatarUriUseCaseImpl(avatarManager)
    }
}