package com.example.esmt.cours.disher.feature_meals.domain.repository

import com.example.esmt.cours.disher.feature_meals.domain.model.CartItem
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

interface MealRepository {

    suspend fun getAllMealsByCategoryFromRemote(category: Category?): List<Meal>

    suspend fun getAllMealsByCategoryFromLocalSource(category: Category?): List<Meal>

    suspend fun getDetailedMealByIdFromRemote(id: Int): Meal?

    suspend fun getDetailedMealByIdFromLocalSource(id: Int): Meal?

    suspend fun searchMealFromRemote(name: String): List<Meal>

    suspend fun searchMealFromLocalSource(search: String): List<Meal>

    suspend fun addMealsToLocalSource(meals: List<Meal>)

    suspend fun deleteAllMealsFromLocalSource()

    suspend fun addMealToFavorites(meal: Meal)

    suspend fun removeMealFromFavorites(meal: Meal)

    suspend fun getFavoriteMeals(): List<Meal>

    suspend fun addMealToCart(meal: Meal)

    suspend fun removeMealFromCart(meal: Meal)

    suspend fun getCart(): List<CartItem>

    suspend fun updateCartItemQuantity(cartItem: CartItem, newQuantity: Int)

}