package com.example.esmt.cours.disher.feature_meals.presentation.search.main_screen

import androidx.compose.ui.focus.FocusManager

sealed class MainSearchUiEvent {

    object PopBackStack: MainSearchUiEvent()
    data class Navigate(val route: String): MainSearchUiEvent()
    data class ShowMealDetails(val id: Int): MainSearchUiEvent()
    data class onDoneMainSearching(val focusManager: FocusManager): MainSearchUiEvent()
    data class RedirectToSearchScreen(val query: String): MainSearchUiEvent()
    data class RedirectToCategoryScreen(val idCategory: Int): MainSearchUiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): MainSearchUiEvent()

}