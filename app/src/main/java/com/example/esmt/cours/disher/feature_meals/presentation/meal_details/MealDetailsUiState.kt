package com.example.esmt.cours.disher.feature_meals.presentation.meal_details

import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.CategoryFeature

data class MealDetailsUiState(
    val isLoading: Boolean = false,
    // Je ne sais pas si c'est une bonne idée ici :
    val detailedMeal: Meal? = null,
    val error: String = "",

    ){

//    fun addCategoryFeature(categoryFeature: CategoryFeature) {
//        val newList = categoryFeatures.toMutableList()
//        newList.add(categoryFeature)
//        categoryFeatures = newList.toList()
//    }

}
