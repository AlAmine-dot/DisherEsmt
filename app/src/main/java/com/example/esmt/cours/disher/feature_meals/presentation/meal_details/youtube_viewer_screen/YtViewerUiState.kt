package com.example.esmt.cours.disher.feature_meals.presentation.meal_details.youtube_viewer_screen

import com.example.esmt.cours.disher.core.common.ConnectivityObserver
import java.util.regex.Pattern

data class YtViewerUiState(
    val isLoading: Boolean = false,
    val mealVideoUrl: String = "",
    val connectivityObserver: ConnectivityObserver,
    val error: String = "",

    ){

    fun getYouTubeVideoId(): String {
        val url = this.mealVideoUrl
        var videoId: String? = null
        val pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(url)
        if (matcher.find()) {
            videoId = matcher.group()
        }
        return videoId.orEmpty()
    }

}

