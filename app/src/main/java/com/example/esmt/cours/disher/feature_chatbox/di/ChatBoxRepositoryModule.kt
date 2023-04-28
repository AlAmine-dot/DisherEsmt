package com.example.esmt.cours.disher.feature_chatbox.di

import com.example.esmt.cours.disher.core.data.local.DisherDatabase
import com.example.esmt.cours.disher.feature_chatbox.data.repository.ChatRepositoryImpl
import com.example.esmt.cours.disher.feature_chatbox.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatBoxRepositoryModule {

    @Provides
    @Singleton
    fun provideChatRepository(db: DisherDatabase): ChatRepository {
        return ChatRepositoryImpl(db)
    }

}