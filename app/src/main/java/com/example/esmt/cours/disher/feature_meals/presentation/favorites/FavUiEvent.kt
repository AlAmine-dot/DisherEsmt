package com.example.esmt.cours.disher.feature_meals.presentation.favorites

import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

sealed class FavUiEvent {

    object PopBackStack: FavUiEvent()
    data class Navigate(val route: String): FavUiEvent()
    data class ShowMealDetails(val id: Int): FavUiEvent()
    data class RemoveMealFromFavorites(val meal: Meal): FavUiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): FavUiEvent()

}