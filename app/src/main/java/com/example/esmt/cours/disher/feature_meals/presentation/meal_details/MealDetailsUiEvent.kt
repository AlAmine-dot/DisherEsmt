package com.example.esmt.cours.disher.feature_meals.presentation.meal_details

sealed class MealDetailsUiEvent{

    object PopBackStack: MealDetailsUiEvent()
    data class Navigate(val route: String): MealDetailsUiEvent()

}
