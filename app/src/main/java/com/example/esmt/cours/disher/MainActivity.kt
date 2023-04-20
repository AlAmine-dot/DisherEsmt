package com.example.esmt.cours.disher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.rememberNavController
import com.example.esmt.cours.disher.core.presentation.graphs.RootNavigationGraph
import com.example.esmt.cours.disher.ui.theme.DisherTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisherTheme {
//                        HomeScreen()
                RootNavigationGraph(navController = rememberNavController())
//                Column(
//                    modifier = Modifier.fillMaxSize().background(Color.Black),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ){
//
////                    YoutubeScreen(videoId = getYouTubeVideoId("https://www.youtube.com/watch?v=-gF8d-fitkU").orEmpty(), modifier = Modifier)
//                }
            }

        }
    }
}

//fun getYouTubeVideoId(url: String): String? {
//    var videoId: String? = null
//    val pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
//    val compiledPattern = Pattern.compile(pattern)
//    val matcher = compiledPattern.matcher(url)
//    if (matcher.find()) {
//        videoId = matcher.group()
//    }
//    return videoId
//}
//
//@Composable
//fun YoutubeScreen(
//    videoId: String,
//    modifier: Modifier
//) {
//    val ctx = LocalContext.current
//    AndroidView(factory = {
//        var view = YouTubePlayerView(it)
//        val fragment = view.addYouTubePlayerListener(
//            object : AbstractYouTubePlayerListener() {
//                override fun onReady(youTubePlayer: YouTubePlayer) {
//                    super.onReady(youTubePlayer)
//                    youTubePlayer.loadVideo(videoId, 0f)
//                }
//            }
//        )
//        view
//    })
//}


//@Composable
//fun VideoPlayer(){
//    val sampleVideo = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
//    val context = LocalContext.current
//    val player = SimpleExoPlayer.Builder(context).build()
//    val playerView = PlayerView(context)
//    val mediaItem = MediaItem.fromUri(sampleVideo)
//    val playWhenReady by rememberSaveable {
//        mutableStateOf(true)
//    }
//    player.setMediaItem(mediaItem)
//    playerView.player = player
//    LaunchedEffect(player) {
//        player.prepare()
//        player.playWhenReady = playWhenReady
//
//    }
//    AndroidView(factory = {
//        playerView
//    })
//}

