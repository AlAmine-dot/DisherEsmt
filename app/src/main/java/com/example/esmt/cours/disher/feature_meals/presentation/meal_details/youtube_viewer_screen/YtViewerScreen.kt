package com.example.esmt.cours.disher.feature_meals.presentation.meal_details.youtube_viewer_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.esmt.cours.disher.core.presentation.main_screen.UiEvent
import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeUiEvent
import com.example.esmt.cours.disher.ui.theme.DarkTurquoise
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.util.regex.Pattern

@Composable
fun YtViewerScreen(
    onNavigate: (HomeUiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    ytVideoUrl: String,
    ytViewerViewModel: YtViewerViewModel = hiltViewModel(),
    sendMainUiEvent: (UiEvent) -> Unit,
) {

    val ytViewerUiState by ytViewerViewModel.uiState.collectAsState()

    LaunchedEffect(ytVideoUrl){
        Log.d("testLink", ytVideoUrl.toString())
        ytViewerViewModel.onEvent(YtViewerUiEvent.onLoadVideo(ytVideoUrl))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkTurquoise),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Log.d("testLink2", ytViewerUiState.getYouTubeVideoId())
        YoutubeScreen(videoId = getYouTubeVideoId(ytVideoUrl), modifier = Modifier)
    }
}

fun getYouTubeVideoId(url: String): String {
    var videoId: String? = null
    val pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
    val compiledPattern = Pattern.compile(pattern)
    val matcher = compiledPattern.matcher(url)
    if (matcher.find()) {
        videoId = matcher.group()
    }
    return videoId.orEmpty()
}

@Composable
fun YoutubeScreen(
    videoId: String,
    modifier: Modifier
) {
    val ctx = LocalContext.current
    AndroidView(factory = {
        var view = YouTubePlayerView(it)
        val fragment = view.addYouTubePlayerListener(
            object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        )
        view
    })
}
