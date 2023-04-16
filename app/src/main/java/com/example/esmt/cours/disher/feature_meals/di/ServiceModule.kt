package com.example.esmt.cours.disher.feature_meals.di

import com.example.esmt.cours.disher.feature_meals.data.local.MealsDatabase
import com.example.esmt.cours.disher.feature_meals.data.remote.api.TheMealApi
import com.example.esmt.cours.disher.feature_meals.data.service.MealService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideMealService(mealApi: TheMealApi, db: MealsDatabase): MealService {
        return MealService(mealApi, db)
    }

}