package com.example.esmt.cours.disher.feature_meals.presentation.home

sealed class HomeUiEvent{

    object PopBackStack: HomeUiEvent()

    data class OnToggleFeedMode(val newFeedMode: FeedMode): HomeUiEvent()
    data class Navigate(val route: String): HomeUiEvent()
    data class ShowMealDetails(val id: Int): HomeUiEvent()

}
