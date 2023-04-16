package com.example.esmt.cours.disher.feature_meals.presentation.favorites

import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

data class FavUiState (

    val isLoading: Boolean = false,
    val favoriteMeals: List<Meal> = emptyList(),
    val error: String = "",
){

    override fun toString(): String {
        return "FavUiState(isLoading=$isLoading, favoriteMeals=$favoriteMeals, error='$error')"
    }
}
