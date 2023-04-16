package com.example.esmt.cours.disher.feature_meals.data.remote.api

import android.util.Log
import com.example.esmt.cours.disher.feature_meals.data.remote.dto.CategoriesDTO
import com.example.esmt.cours.disher.feature_meals.data.remote.dto.DetailedMealsDTO
import com.example.esmt.cours.disher.feature_meals.data.remote.dto.MealsByCategoryDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class TheMealApiImpl @Inject constructor(httpClient: HttpClient): TheMealApi {

    private val client = httpClient

    override suspend fun getAllCategories(): CategoriesDTO {

        return try {
            client.get("https://www.themealdb.com/api/json/v1/1/categories.php").body()
        }catch (e: Exception) {
            Log.e("Erreur", "Une erreur s'est produite lors de la requête getAllCategories: ${e.message}, ${e.cause}, ${e.stackTrace}")
            CategoriesDTO()
        }

    }

    override suspend fun getAllMealsByCategory(categoryName: String): MealsByCategoryDTO {

        return try {
            client.get("https://www.themealdb.com/api/json/v1/1/filter.php?c=${categoryName}").body()
        }catch (e: Exception) {
            Log.e("Erreur", "Une erreur s'est produite lors de la requête getAllMealsByCategory (${categoryName}): ${e.message}, ${e.cause}, ${e.stackTrace}")
            MealsByCategoryDTO()
        }

    }

    override suspend fun getDetailedMealById(id: Int): DetailedMealsDTO {

        return try {
            client.get("https://www.themealdb.com/api/json/v1/1/lookup.php?i=${id}").body()
        }catch (e: Exception) {
            Log.e("Erreur", "Une erreur s'est produite lors de la requête getDetailedMealById (${id}): ${e.message}, ${e.cause}, ${e.stackTrace}")
            DetailedMealsDTO()
        }

    }

    override suspend fun searchMealByName(name: String): DetailedMealsDTO {

        return try {
            client.get("https://www.themealdb.com/api/json/v1/1/search.php?s=${name}").body()
        }catch (e: Exception) {
            Log.e("Erreur", "Une erreur s'est produite lors de la requête searchMealByName (${name}): ${e.message}, ${e.cause}, ${e.stackTrace}")
            DetailedMealsDTO()
        }

    }

}