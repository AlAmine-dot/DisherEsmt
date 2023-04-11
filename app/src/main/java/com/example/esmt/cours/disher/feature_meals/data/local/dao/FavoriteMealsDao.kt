package com.example.esmt.cours.disher.feature_meals.data.local.dao

import androidx.room.*
import com.example.esmt.cours.disher.core.util.Constants
import com.example.esmt.cours.disher.feature_meals.data.local.entities.FavoriteMealItemEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.MealEntity

@Dao
interface FavoriteMealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMealToFavorite(meal: FavoriteMealItemEntity)

    @Transaction
    @Query("DELETE FROM ${Constants.FAVORITE_MEALS_TABLE} WHERE mealId = :mealId")
    suspend fun removeMealFromFavorite(mealId: Int)

}