package com.example.esmt.cours.disher.feature_meals.presentation.home

import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

data class HomeState(
    val isLoading: Boolean = false,
    val meals: List<Meal> = emptyList(),
    val mealCategories: List<Category> = emptyList(),
    val error: String = "",

){
    companion object {
        const val MEALS_PAGE_SIZE: Int = 6
    }

    override fun toString(): String {
        return "HomeState(isLoading=$isLoading, meals=$meals, error='$error')"
    }
}
