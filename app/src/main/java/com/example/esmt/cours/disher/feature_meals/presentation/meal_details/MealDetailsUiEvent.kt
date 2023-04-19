package com.example.esmt.cours.disher.feature_meals.presentation.meal_details

import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

sealed class MealDetailsUiEvent{

    object PopBackStack: MealDetailsUiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): MealDetailsUiEvent()

    data class Navigate(val route: String): MealDetailsUiEvent()
    data class RedirectToURI(val uri: String?) : MealDetailsUiEvent()
    data class ToggleMealFromFavorite(val meal: Meal) : MealDetailsUiEvent()
    data class ToggleMealDetailsOption(val newOption: MealDetailsOption): MealDetailsUiEvent()
}
