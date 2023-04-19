package com.example.esmt.cours.disher.feature_meals.presentation.search.category_details_screen

import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.CategoryFeature

data class CategoryUiState (

    val isLoading: Boolean = false,
    val categoryFeature: CategoryFeature? = null,
    val nbRecipes : Int = 0,
    val searchResult: List<Meal> = emptyList(),
    val error: String = "",


){

    companion object {
        const val MEALS_PAGE_SIZE: Int = (-1)
    }

    override fun toString(): String {
        return "CategoryUiState(isLoading=$isLoading, categoryFeature=$categoryFeature, nbRecipes=$nbRecipes, searchResult=$searchResult, error='$error')"
    }
}
