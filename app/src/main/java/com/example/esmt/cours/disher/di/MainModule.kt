package com.example.esmt.cours.disher.di

import android.content.Context
import com.example.esmt.cours.disher.core.common.NetworkConnectivityObserver
import com.example.esmt.cours.disher.core.data.local.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext appContext: Context): NetworkConnectivityObserver {
        return NetworkConnectivityObserver(appContext)
    }
    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)

}