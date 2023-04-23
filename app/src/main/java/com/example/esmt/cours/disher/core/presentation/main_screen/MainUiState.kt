package com.example.esmt.cours.disher.core.presentation.main_screen

import com.example.esmt.cours.disher.core.common.ConnectivityObserver
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.CategoryFeature

data class MainUiState(
    val isLoading: Boolean = false,
    val connectivityObserver: ConnectivityObserver,
    val isBottomBarVisible: Boolean = true,
    val error: String = "",

    ){

}
