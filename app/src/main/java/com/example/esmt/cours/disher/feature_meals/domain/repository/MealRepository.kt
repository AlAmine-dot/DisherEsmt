package com.example.esmt.cours.disher.feature_meals.domain.repository

import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

interface MealRepository {

//    suspend fun getAllCategoriesFromRemote(): List<Category>

//    suspend fun getAllCategoriesFromLocalSource(): List<Category>

    suspend fun getAllMealsByCategoryFromRemote(category: Category?): List<Meal>

    suspend fun getAllMealsByCategoryFromLocalSource(category: Category?): List<Meal>

    suspend fun getDetailedMealByIdFromRemote(id: Int): Meal?

    suspend fun getDetailedMealByIdFromLocalSource(id: Int): Meal?

    suspend fun searchMealFromRemote(name: String): List<Meal>

    suspend fun searchMealFromLocalSource(search: String): List<Meal>

    suspend fun addMealsToLocalSource(meals: List<Meal>)

//    suspend fun addCategoriesToLocalSource(categories: List<Category>)

    suspend fun deleteAllMealsFromLocalSource()

    suspend fun addMealToFavorites(meal: Meal)

    suspend fun removeMealFromFavorites(meal: Meal)
    // Méthodes et use-cases pas encore implémentées :
    // Delete all meals of a category from Local Source

    suspend fun getFavoriteMeals(): List<Meal>

}