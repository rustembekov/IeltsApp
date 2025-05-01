package com.example.support.core.di


import com.example.support.core.util.AvatarManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AvatarModule {

    @Provides
    @Singleton
    fun provideAvatarManager(): AvatarManager = AvatarManager()
}
