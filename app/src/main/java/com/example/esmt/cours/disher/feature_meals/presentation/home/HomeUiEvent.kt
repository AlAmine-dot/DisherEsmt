package com.example.esmt.cours.disher.feature_meals.presentation.home

import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.AlertDialogState

sealed class HomeUiEvent{

    object PopBackStack: HomeUiEvent()
    object RefreshCart: HomeUiEvent()
    object OnHideAlertDialog: HomeUiEvent()
    object OnDiscardCart: HomeUiEvent()
    data class AddMealToCart(val meal: Meal): HomeUiEvent()
    data class OnShowAlertDialog(val newAlertDialog: AlertDialogState): HomeUiEvent()
    data class GenerateRandomMenu(val n: Int): HomeUiEvent()

    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): HomeUiEvent()


    data class OnToggleFeedMode(val newFeedMode: FeedMode): HomeUiEvent()

    data class Navigate(val route: String): HomeUiEvent()
    data class ShowMealDetails(val id: Int): HomeUiEvent()

}
