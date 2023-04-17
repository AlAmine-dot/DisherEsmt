package com.example.esmt.cours.disher.feature_meals.presentation.search.main_screen

import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

data class MainSearchUiState (

    val isSearching: Boolean = false,
    val mainSearchText: String = "",
    val error: String = "",
){

}
