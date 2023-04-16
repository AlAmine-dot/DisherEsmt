package com.example.esmt.cours.disher.feature_meals.data.service

import com.example.esmt.cours.disher.feature_meals.data.local.MealsDatabase
import com.example.esmt.cours.disher.feature_meals.data.local.entities.CategoryEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.FavoriteMealItemEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.MealEntity
import com.example.esmt.cours.disher.feature_meals.data.remote.api.TheMealApi
import com.example.esmt.cours.disher.feature_meals.data.remote.dto.CategoriesDTO
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import javax.inject.Inject

class MealService @Inject constructor(
    private val api: TheMealApi,
    private val db: MealsDatabase
) {

    private val mealsDao = db.mealsDao()
    private val favoritesDao = db.favoriteMealsDao()
    private val categoriesDao = db.categoriesDao()

    // IS MEAL FAVORITE :

    suspend fun isMealFavorite(mealEntity: MealEntity): Boolean {
        return favoritesDao.isMealFavorite(mealId = mealEntity.mealId)
    }

    // GET MEALS BY CATEGORY :

    suspend fun getMealsByCategoryFromRemote(str: String): List<Meal>  {
        val response = api.getAllMealsByCategory(str).meals.map {
            api.getDetailedMealById(it.idMeal).meals.firstOrNull()
        }

        if(response.isNullOrEmpty()){
            return emptyList()
        }else{
            // J'ai pas mal de questions ici, il faudra revoir ça
            return response.filterNotNull().map { it.toMeal() }
        }
    }

    suspend fun getMealsByCategoryFromLocalSource(str: String): List<Meal>{
        val response = mealsDao.getAllMealsByCategory(str).map {
            it.toMeal()
        }

        return response
    }


    // SEARCH MEAL :

    suspend fun searchMealFromRemote(str: String): List<Meal> {
        val response = api.searchMealByName(str).meals

        if(response.isNullOrEmpty()){
            return emptyList()
        }else{
            return response.map { it.toMeal() }
        }
    }


    suspend fun searchMealFromLocalSource(str: String): List<Meal> {
        val response = mealsDao.searchMealByName(str)

        if(response.isEmpty()){
            return emptyList()
        }else{
            return response.map { it.toMeal() }
        }

    }

    suspend fun getFavoriteMealsFromLocalSource() : List<Meal> {
        val response = favoritesDao.getAllFavorites()

        return response.map { it.FavoriteMeal.toMeal() }
    }


    // ADD MEALS TO LOCAL SOURCE :

    suspend fun addMealsToLocalSource(mealEntities: List<MealEntity>){
        mealsDao.addMeals(mealEntities)
    }

    // ADD CATEGORIES TO LOCAL SOURCE :

    suspend fun addCategoriesToLocalSource(categoryEntities: List<CategoryEntity>){
        categoriesDao.addCategories(categoryEntities)
    }

    // REMOVE ALL MEALS FROM LOCAL SOURCE :

    suspend fun removeAllMealsFromLocalSource(){
        mealsDao.deleteAllMeals()
    }

    // REMOVE ALL MEALS OF A CATEGORY FROM LOCAL SOURCE :

    suspend fun removeAllMealsOfACategoryFromLocalSource(category: String){
        mealsDao.deleteAllMealsOfCategory(category)
    }

    // GET A MEAL BY ITS ID :

    suspend fun getMealByIdFromLocalSource(id: Int): MealEntity{
        val response = mealsDao.getMealById(id)

        return response
    }

    suspend fun getMealByIdFromRemote(id: Int): Meal? {
        val response = api.getDetailedMealById(id).meals.firstOrNull()
//        val response = api.getDetailedMealById(id).meals?.let{
//            if(it.isEmpty()){
//                null
//            }else{
//                it.first().toMeal()
//            }
//        }

        return response?.toMeal()
    }

    // GET ALL CATEGORIES FROM REMOTE :

    suspend fun getAllCategoriesFromRemote(): List<CategoriesDTO.CategoryItemDTO>{
        val response = api.getAllCategories().categories

        return response
    }

    // GET ALL CATEGORIES FROM LOCALSOURCE :

    suspend fun getAllCategoriesFromLocalSource(): List<CategoryEntity>{
        val response = categoriesDao.getAllCategories()

        return response
    }


    // ADD MEAL TO FAVORITE :

    suspend fun addMealToFavorite(mealEntity: MealEntity){

        favoritesDao.addMealToFavorite(FavoriteMealItemEntity(0 , mealEntity))
        mealsDao.addMeals(listOf(mealEntity.copy(isFavorite = true)))
    }

    // REMOVE MEAL FROM FAVORITE FAVORITE :

    suspend fun removeMealFromFavorite(mealEntity: MealEntity){

        favoritesDao.removeMealFromFavorite(mealEntity.mealId)
        mealsDao.addMeals(listOf(mealEntity.copy(isFavorite = false)))
    }

}