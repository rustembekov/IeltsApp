package com.example.support.core.di

import com.example.support.core.util.GameResultManager
import com.example.support.core.util.GameResultManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GameResultManagerModule {

    @Binds
    @Singleton
    abstract fun bindGameResultManager(
        impl: GameResultManagerImpl
    ): GameResultManager
}
