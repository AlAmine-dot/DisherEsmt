package com.example.esmt.cours.disher.feature_meals.data.local.dao

import androidx.room.*
import com.example.esmt.cours.disher.core.common.util.Constants
import com.example.esmt.cours.disher.feature_meals.data.local.entities.CartItemEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.MealEntity

@Dao
interface CartMealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMealToCart(meal: CartItemEntity)

    @Transaction
    @Query("DELETE FROM ${Constants.CART_MEALS_TABLE} WHERE mealId = :mealId")
    suspend fun removeMealOfCart(mealId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM ${Constants.CART_MEALS_TABLE} WHERE mealId = :mealId LIMIT 1)")
    suspend fun isMealIntoCart(mealId: Int): Boolean

    @Query("SELECT * FROM ${Constants.CART_MEALS_TABLE}")
    suspend fun getAllCartsMeal(): List<CartItemEntity>

    @Query("UPDATE ${Constants.CART_MEALS_TABLE} SET CartMealItemQuantity = :newQuantity WHERE mealId = :mealId")
    suspend fun updateQuantity(mealId: Int, newQuantity: Int)

    @Query("DELETE FROM ${Constants.CART_MEALS_TABLE}")
    suspend fun clearCart()


}