package com.example.esmt.cours.disher.feature_meals.di

import com.example.esmt.cours.disher.feature_meals.data.repository.MealRepositoryImpl
import com.example.esmt.cours.disher.feature_meals.data.service.MealService
import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MealsRepositoryModule{

//    @Provides
//    @Singleton
//    fun provideMealsDatabase(app: Application): DisherDatabase {
//        return Room.databaseBuilder(
//            app,
//            DisherDatabase::class.java,
//            Constants.DISHER_DATABASE).fallbackToDestructiveMigration()
//            .build()
//    }

    @Provides
    @Singleton
    fun provideMealRepository(mealService: MealService): MealRepository{
        return MealRepositoryImpl(mealService)
    }
}