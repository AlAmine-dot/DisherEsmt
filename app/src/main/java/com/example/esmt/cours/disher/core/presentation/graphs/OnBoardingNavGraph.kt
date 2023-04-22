package com.example.esmt.cours.disher.core.presentation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.esmt.cours.disher.core.presentation.onboarding.OnBoardingContent
//import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.onBoardNavGraph(navController: NavHostController, onClick: () -> Unit){
    navigation(
        route = Graph.ONBOARDING.route,
        startDestination = OnBoardingScreen.Welcome.route
    ) {
        composable(route = OnBoardingScreen.Welcome.route){
            OnBoardingContent(onClick)
        }
    }
}

sealed class OnBoardingScreen(val route: String) {
    object Welcome : OnBoardingScreen(route = "WELCOME")
}