package com.example.esmt.cours.disher.feature_meals.data.remote.api

import com.example.esmt.cours.disher.feature_meals.data.remote.dto.CategoriesDTO
import com.example.esmt.cours.disher.feature_meals.data.remote.dto.DetailedMealsDTO
import com.example.esmt.cours.disher.feature_meals.data.remote.dto.MealsByCategoryDTO

interface TheMealApi {

    suspend fun getAllCategories(): CategoriesDTO

    suspend fun getAllMealsByCategory(categoryName: String): MealsByCategoryDTO

    suspend fun getDetailedMealById(id: Int): DetailedMealsDTO

    suspend fun searchMealByName(name: String): DetailedMealsDTO
}