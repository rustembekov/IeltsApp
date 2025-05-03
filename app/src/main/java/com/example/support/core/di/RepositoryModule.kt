package com.example.support.core.di
import android.content.Context
import com.example.support.core.data.UserRepository
import com.example.support.feature.essaybuilder.presentation.repository.EssayBuilderGameRepository
import com.example.support.feature.factopinion.presentation.repository.FactOpinionGameRepository
import com.example.support.feature.gamecompletion.presentation.repository.GameCompletionRepository
import com.example.support.feature.gamecompletion.presentation.repository.GameCompletionRepositoryImpl
import com.example.support.feature.keywordscheck.presentation.repository.KeywordsCheckGameRepository
import com.example.support.feature.phrasalverbs.presentation.repository.PhrasalVerbsGameRepository
import com.example.support.feature.rating.presentation.repository.AvatarRepository
import com.example.support.feature.rating.presentation.repository.AvatarRepositoryImpl
import com.example.support.feature.synonyms.presentation.repository.SynonymsGameRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideDatabaseReference(): DatabaseReference = Firebase.database.reference

    @Provides
    @Singleton
    fun provideUserRepository(
        firebaseAuth: FirebaseAuth,
        database: DatabaseReference
    ): UserRepository {
        return UserRepository(firebaseAuth, database)
    }

    @Provides
    @Singleton
    fun providePhrasalVerbsRepository(database: DatabaseReference): PhrasalVerbsGameRepository {
        return PhrasalVerbsGameRepository(database)
    }

    @Provides
    @Singleton
    fun provideFactOpinionRepository(database: DatabaseReference): FactOpinionGameRepository {
        return FactOpinionGameRepository(database)
    }

    @Provides
    @Singleton
    fun provideSynonymsRepository(database: DatabaseReference): SynonymsGameRepository {
        return SynonymsGameRepository(database)
    }

    @Provides
    @Singleton
    fun provideKeywordsCheckRepository(database: DatabaseReference): KeywordsCheckGameRepository {
        return KeywordsCheckGameRepository(database)
    }

    @Provides
    @Singleton
    fun provideEssayBuilderRepository(database: DatabaseReference): EssayBuilderGameRepository {
        return EssayBuilderGameRepository(database)
    }

    @Provides
    @Singleton
    fun provideGameCompletionRepository(database: DatabaseReference): GameCompletionRepository {
        return GameCompletionRepositoryImpl(database)
    }

    @Provides
    fun provideAvatarRepository(
        @ApplicationContext context: Context
    ): AvatarRepository = AvatarRepositoryImpl(context)

}