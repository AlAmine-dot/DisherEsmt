package com.example.esmt.cours.disher.feature_meals.di

import com.example.esmt.cours.disher.core.data.local.DisherDatabase
import com.example.esmt.cours.disher.feature_meals.data.remote.api.TheMealApi
import com.example.esmt.cours.disher.feature_meals.data.service.CategoryService
import com.example.esmt.cours.disher.feature_meals.data.service.MealService
import com.example.esmt.cours.disher.feature_meals.domain.utils.CategoryManager
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
    fun provideMealService(mealApi: TheMealApi, db: DisherDatabase): MealService {
        return MealService(mealApi, db)
    }

    @Provides
    @Singleton
    fun provideCategoryService(mealApi: TheMealApi, db: DisherDatabase): CategoryService {
        return CategoryService(mealApi,db)
    }

    @Provides
    @Singleton
    fun provideCategoryManager(categoryService: CategoryService): CategoryManager {
        return CategoryManager(categoryService)
    }


}