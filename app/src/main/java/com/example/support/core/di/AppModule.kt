package com.example.support.core.di

import com.example.support.core.data.UserManager
import com.example.support.core.data.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindUserManager(
        userRepository: UserRepository
    ): UserManager
}
