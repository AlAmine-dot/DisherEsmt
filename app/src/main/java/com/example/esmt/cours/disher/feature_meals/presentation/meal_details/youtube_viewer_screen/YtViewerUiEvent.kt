package com.example.esmt.cours.disher.feature_meals.presentation.meal_details.youtube_viewer_screen

import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

sealed class YtViewerUiEvent{

    object PopBackStack: YtViewerUiEvent()
    data class onLoadVideo(val videoUrl: String): YtViewerUiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): YtViewerUiEvent()

    data class Navigate(val route: String): YtViewerUiEvent()
}
