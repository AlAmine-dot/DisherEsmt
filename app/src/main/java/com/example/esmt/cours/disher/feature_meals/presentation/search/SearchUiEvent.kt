package com.example.esmt.cours.disher.feature_meals.presentation.search

import androidx.compose.ui.focus.FocusManager
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.meal_details.MealDetailsUiEvent

sealed class SearchUiEvent {

    object PopBackStack: SearchUiEvent()
    data class Navigate(val route: String): SearchUiEvent()
    data class ShowMealDetails(val id: Int): SearchUiEvent()
    data class onDoneSearching(val focusManager: FocusManager): SearchUiEvent()
//    data class RemoveMealFromFavorites(val meal: Meal): SearchUiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): SearchUiEvent()

}