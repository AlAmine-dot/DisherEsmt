package com.example.esmt.cours.disher.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.esmt.cours.disher.core.common.NetworkConnectivityObserver
import com.example.esmt.cours.disher.core.common.util.Constants
import com.example.esmt.cours.disher.core.data.local.DisherDatabase
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
    fun provideMealsDatabase(app: Application): DisherDatabase {
        return Room.databaseBuilder(
            app,
            DisherDatabase::class.java,
            Constants.DISHER_DATABASE).fallbackToDestructiveMigration()
            .build()
    }

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