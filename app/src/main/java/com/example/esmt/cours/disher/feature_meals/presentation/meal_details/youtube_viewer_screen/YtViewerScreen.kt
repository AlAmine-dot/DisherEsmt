package com.example.esmt.cours.disher.feature_meals.presentation.meal_details.youtube_viewer_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.core.common.ConnectivityObserver
import com.example.esmt.cours.disher.core.presentation.main_screen.UiEvent
import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeUiEvent
import com.example.esmt.cours.disher.ui.theme.DarkTurquoise
import com.example.esmt.cours.disher.ui.theme.MeltyGreen
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@Composable
fun YtViewerScreen(
    onNavigate: (HomeUiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    ytVideoUrl: String,
    ytViewerViewModel: YtViewerViewModel = hiltViewModel(),
    sendMainUiEvent: (UiEvent) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val ytViewerUiState by ytViewerViewModel.uiState.collectAsState()
    var isLoading = remember { mutableStateOf(true) }
    val status by ytViewerUiState.connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Unavailable
    )

    LaunchedEffect(ytVideoUrl) {
        Log.d("testLink", ytVideoUrl.toString())
        ytViewerViewModel.onEvent(YtViewerUiEvent.onLoadVideo(ytVideoUrl))
        coroutineScope.launch {
            delay(2000)
            isLoading.value = false
        }
    }

    if(isLoading.value) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkTurquoise),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(color = MeltyGreen)
        }
    }else {


        if (status == ConnectivityObserver.Status.Available) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkTurquoise),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Log.d("testLink2", ytViewerUiState.getYouTubeVideoId())

                YoutubeScreen(videoId = getYouTubeVideoId(ytVideoUrl), modifier = Modifier)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkTurquoise),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                ) {

                    Image(
                        painterResource(id = R.drawable.ph_offline),
                        contentDescription = "No connection placeholder",
                    )

                }
                Text(
                    "You're offline.",
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
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
