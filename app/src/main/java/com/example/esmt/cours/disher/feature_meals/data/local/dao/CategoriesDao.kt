package com.example.esmt.cours.disher.feature_meals.data.local.dao

import androidx.room.*
import com.example.esmt.cours.disher.core.util.Constants
import com.example.esmt.cours.disher.feature_meals.data.local.entities.CategoryEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.FavoriteMealItemEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.MealEntity

@Dao
interface CategoriesDao {

    @Transaction
    @Query("SELECT * FROM ${Constants.CATEGORIES_TABLE}")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories(categories: List<CategoryEntity>)

    @Query("DELETE FROM ${Constants.CATEGORIES_TABLE}")
    suspend fun deleteAllCategories()

    @Transaction
    @Query("SELECT * FROM ${Constants.CATEGORIES_TABLE} WHERE idCategory = :idCategory ")
    suspend fun getCategoryById(idCategory: Int): CategoryEntity

}