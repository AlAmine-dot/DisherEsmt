package com.example.esmt.cours.disher.feature_meals.data.repository

import com.example.esmt.cours.disher.feature_meals.data.service.MealService
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealService : MealService
): MealRepository {

    // TESTED
    override suspend fun getAllCategoriesFromRemote(): List<Category> {

        return mealService.getAllCategoriesFromRemote().map{ it.toCategory() }

    }


    override suspend fun getAllCategoriesFromLocalSource(): List<Category> {

        return mealService.getAllCategoriesFromLocalSource().map{ it.toCategory() }

    }

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

    // TESTED (NOT WORKING YET)
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
                isFavorite = mealService.isMealFavorite(it)
            )
        }
        mealService.addMealsToLocalSource(newList)
    }

    override suspend fun getFavoriteMeals() : List<Meal>{
        return mealService.getFavoriteMealsFromLocalSource()
    }

    override suspend fun addCategoriesToLocalSource(categories: List<Category>) {
        val newList = categories.map { it.toCategoryEntity() }
        mealService.addCategoriesToLocalSource(newList)
    }

    // TESTED
    override suspend fun deleteAllMealsFromLocalSource() {
        mealService.removeAllMealsFromLocalSource()
    }

    override suspend fun addMealToFavorites(meal: Meal) {
        mealService.addMealToFavorite(meal.toMealEntity())
    }

    override suspend fun removeMealFromFavorites(meal: Meal) {
        mealService.removeMealFromFavorite(meal.toMealEntity())
    }

}