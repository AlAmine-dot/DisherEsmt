package com.example.esmt.cours.disher.feature_meals.data.service

import com.example.esmt.cours.disher.feature_meals.data.local.MealsDatabase
import com.example.esmt.cours.disher.feature_meals.data.local.entities.CategoryEntity
import com.example.esmt.cours.disher.feature_meals.data.remote.api.TheMealApi
import com.example.esmt.cours.disher.feature_meals.data.remote.dto.CategoriesDTO
import javax.inject.Inject

class CategoryService @Inject constructor(
    private val api: TheMealApi,
    private val db: MealsDatabase
) {
    private val categoriesDao = db.categoriesDao()

    // ADD CATEGORIES TO LOCAL SOURCE :

    suspend fun addCategoriesToLocalSource(categoryEntities: List<CategoryEntity>){
        categoriesDao.addCategories(categoryEntities)
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


}