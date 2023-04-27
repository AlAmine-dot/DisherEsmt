package com.example.esmt.cours.disher.feature_meals.presentation.home

import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.meal_details.details_screen.MealDetailsUiEvent

sealed class HomeUiEvent{

    object PopBackStack: HomeUiEvent()
    object RefreshCart: HomeUiEvent()
    data class AddMealToCart(val meal: Meal): HomeUiEvent()

    data class GenerateRandomMenu(val n: Int): HomeUiEvent()

    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): HomeUiEvent()


    data class OnToggleFeedMode(val newFeedMode: FeedMode): HomeUiEvent()

    data class Navigate(val route: String): HomeUiEvent()
    data class ShowMealDetails(val id: Int): HomeUiEvent()

}
