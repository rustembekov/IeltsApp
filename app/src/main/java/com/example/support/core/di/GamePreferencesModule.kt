package com.example.support.core.di

import com.example.support.core.data.GamePreferences
import com.example.support.core.data.GamePreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GamePreferencesModule {

    @Binds
    @Singleton
    abstract fun bindGamePreferences(
        impl: GamePreferencesImpl
    ): GamePreferences
}
