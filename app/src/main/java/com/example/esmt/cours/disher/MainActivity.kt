package com.example.esmt.cours.disher

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.esmt.cours.disher.core.presentation.graphs.Graph
import com.example.esmt.cours.disher.core.presentation.graphs.RootNavigationGraph
import com.example.esmt.cours.disher.core.presentation.onboarding.SplashViewModel
import com.example.esmt.cours.disher.feature_chatbox.presentation.chatbox.ChatboxScreen
import com.example.esmt.cours.disher.ui.theme.DisherTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel


    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }

        setContent {
            DisherTheme {
                val screen = splashViewModel.startDestination.value
                Log.d("testmain",screen)
                val navController = rememberNavController()
                RootNavigationGraph(navController = navController, startDestination = screen)
            }

        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    DisherTheme {
//        Greeting("Android")
//    }
//}