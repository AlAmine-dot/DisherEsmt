package com.example.esmt.cours.disher.feature_meals.data.local.dao

import androidx.room.*
import com.example.esmt.cours.disher.core.util.Constants
import com.example.esmt.cours.disher.feature_meals.data.local.entities.CategoryEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.MealEntity

@Dao
interface MealsDao {

    // TESTED
    @Transaction
    @Query("SELECT * FROM ${Constants.MEALS_TABLE}")
    suspend fun getAllMeals(): List<MealEntity>


    // TESTED
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMeals(meals: List<MealEntity>)

    // TESTED
    @Transaction
    @Query("SELECT * FROM ${Constants.MEALS_TABLE} WHERE strCategory = :strCategory ")
    suspend fun getAllMealsByCategory(strCategory: String): List<MealEntity>

    @Transaction
    @Query("DELETE FROM ${Constants.MEALS_TABLE} WHERE strCategory = :strCategory ")
    suspend fun deleteAllMealsOfCategory(strCategory: String)

    @Query("DELETE FROM ${Constants.MEALS_TABLE}")
    suspend fun deleteAllMeals()

    @Transaction
    @Query("SELECT * FROM ${Constants.MEALS_TABLE} WHERE mealId = :idMeal ")
    suspend fun getMealById(idMeal: Int): MealEntity

    @Transaction
    @Query("SELECT * FROM ${Constants.MEALS_TABLE} WHERE strMeal LIKE '%' || :name || '%'")
    suspend fun searchMealByName(name: String): List<MealEntity>

}

