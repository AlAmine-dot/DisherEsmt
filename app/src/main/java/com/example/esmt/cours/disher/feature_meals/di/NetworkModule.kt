package com.example.esmt.cours.disher.feature_meals.di

import com.example.esmt.cours.disher.feature_meals.data.local.dao.FavoriteMealsDao
import com.example.esmt.cours.disher.feature_meals.data.local.dao.MealsDao
import com.example.esmt.cours.disher.feature_meals.data.remote.api.TheMealApi
import com.example.esmt.cours.disher.feature_meals.data.remote.api.TheMealApiImpl
import com.example.esmt.cours.disher.feature_meals.data.service.MealService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
                install(Logging) {
                    level = LogLevel.ALL
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideMealApi(httpClient: HttpClient): TheMealApi {
        return TheMealApiImpl(httpClient)
    }

}