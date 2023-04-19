package com.example.esmt.cours.disher.feature_meals.presentation.search.category_details_screen

import androidx.compose.ui.focus.FocusManager

sealed class CategoryUiEvent {

    object PopBackStack: CategoryUiEvent()
    data class Navigate(val route: String): CategoryUiEvent()
    data class ShowMealDetails(val id: Int): CategoryUiEvent()
//    data class RemoveMealFromFavorites(val meal: Meal): SearchUiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): CategoryUiEvent()

}