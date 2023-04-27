package com.example.esmt.cours.disher.feature_meals.data.repository

import com.example.esmt.cours.disher.feature_meals.data.service.MealService
import com.example.esmt.cours.disher.feature_meals.domain.model.CartItem
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealService : MealService
): MealRepository {

    // TESTED
    override suspend fun getAllMealsByCategoryFromRemote(category: Category?): List<Meal> {

        val query = category?.categoryName.orEmpty()

        // Normalement la conversion devrait se faire dans le domain mais le retour bizarre
        // de l'API me complique la tache pour instancier dans les classes de test.
        return mealService.getMealsByCategoryFromRemote(query)

    }

    // TESTED
    override suspend fun getAllMealsByCategoryFromLocalSource(category: Category?): List<Meal> {

        val query = category?.categoryName.orEmpty()

        // Normalement la conversion devrait se faire dans le domain mais le retour bizarre
        // de l'API me complique la tache pour instancier dans les classes de test.
        return mealService.getMealsByCategoryFromLocalSource(query)

    }

    // TESTED
    override suspend fun getDetailedMealByIdFromRemote(id: Int): Meal? {

        return mealService.getMealByIdFromRemote(id)

    }

    // TESTED
    override suspend fun getDetailedMealByIdFromLocalSource(id: Int): Meal? {

        return mealService.getMealByIdFromLocalSource(id)?.let {
            it.toMeal()
        }

    }

    // TESTED
    override suspend fun searchMealFromRemote(name: String): List<Meal> {

        return mealService.searchMealFromRemote(name)

    }

    // TESTED
    override suspend fun searchMealFromLocalSource(name: String): List<Meal> {

        return mealService.searchMealFromLocalSource(name)

    }

    // TESTED
    override suspend fun addMealsToLocalSource(meals: List<Meal>) {
        var newList = meals.map { it.toMealEntity() }
        newList = newList.map {
            it.copy(
                isFavorite = mealService.isMealFavorite(it),
                isIntoCart = mealService.isMealIntoCart(it)
            )
        }
        mealService.addMealsToLocalSource(newList)
    }

    // TESTED
    override suspend fun getFavoriteMeals() : List<Meal>{
        return mealService.getFavoriteMealsFromLocalSource()
    }

    //////////////////////////////////////////////////////////

    // TESTED
    override suspend fun addMealToCart(meal: Meal) {
        mealService.addMealToCart(meal.toMealEntity())
    }

    // TESTED
    override suspend fun removeMealFromCart(meal: Meal) {
        mealService.removeMealFromCart(meal.toMealEntity())
    }

    // TESTED
    override suspend fun getCart(): List<CartItem> {
        return mealService.getCart()
    }

    // TESTED
    override suspend fun updateCartItemQuantity(cartItem: CartItem, newQuantity: Int) {
        mealService.updateCartItemQuantity(cartItem.cartItemMeal.toMealEntity(),newQuantity)
    }

    override suspend fun isMealIntoCart(meal: Meal): Boolean {
        return mealService.isMealIntoCart(meal.toMealEntity())
    }

    override suspend fun getRandomMealsFromLocalSource(n: Int): List<Meal> {
        return mealService.getRandomMealsFromLocalSource(n)
    }


    // TESTED
    override suspend fun deleteAllMealsFromLocalSource() {
        mealService.removeAllMealsFromLocalSource()
    }

    // TESTED
    override suspend fun addMealToFavorites(meal: Meal) {
        mealService.addMealToFavorite(meal.toMealEntity())
    }

    // TESTED
    override suspend fun removeMealFromFavorites(meal: Meal) {
        mealService.removeMealFromFavorite(meal.toMealEntity())
    }

}