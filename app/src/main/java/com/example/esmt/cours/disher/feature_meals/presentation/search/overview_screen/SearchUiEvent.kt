package com.example.esmt.cours.disher.feature_meals.presentation.search.overview_screen

import androidx.compose.ui.focus.FocusManager

sealed class SearchUiEvent {

    object PopBackStack: SearchUiEvent()
    data class Navigate(val route: String): SearchUiEvent()
    data class ShowMealDetails(val id: Int): SearchUiEvent()
    data class onDoneSearching(val focusManager: FocusManager?): SearchUiEvent()
//    data class RemoveMealFromFavorites(val meal: Meal): SearchUiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): SearchUiEvent()

}