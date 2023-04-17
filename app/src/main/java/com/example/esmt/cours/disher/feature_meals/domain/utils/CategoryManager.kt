package com.example.esmt.cours.disher.feature_meals.domain.utils

import com.example.esmt.cours.disher.feature_meals.data.service.CategoryService
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryManager @Inject constructor(categoryService: CategoryService){

    private var categories : MutableList<Category>

    init {
        categories = mutableListOf()
        runBlocking {
            initializeCategoryManager(categoryService)
        }
    }


    fun addCategories(categoryList: List<Category>) {
        categoryList.forEach{ category ->
            categories.add(category)
        }
    }

    fun getCategoryByName(name: String): Category? {
        return categories.find { it.categoryName == name }
    }

    fun getCategory(index: Int): Category? {
        if (index < 0 || index >= categories.size) {
            return null
        }
        return categories[index]
    }

    fun getAllCategories(): List<Category> {
        return categories.toList()
    }

    suspend fun initializeCategoryManager(categoryService: CategoryService){
        val localResponse = categoryService.getAllCategoriesFromLocalSource()
        val remoteResponse = categoryService.getAllCategoriesFromRemote()

        if(localResponse.isEmpty() || localResponse != remoteResponse){
            categoryService.addCategoriesToLocalSource(remoteResponse.map { it.toCategory().toCategoryEntity() })
            val finalResponse = categoryService.getAllCategoriesFromLocalSource()
            this.addCategories(finalResponse.map { it.toCategory() })
        }
        else {
            val finalResponse = localResponse
            this.addCategories(finalResponse.map { it.toCategory() })
        }
    }
}
