package com.example.esmt.cours.disher.feature_meals.presentation.meal_details.youtube_viewer_screen

import java.util.regex.Pattern

data class YtViewerUiState(
    val isLoading: Boolean = false,
    val mealVideoUrl: String = "",
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

