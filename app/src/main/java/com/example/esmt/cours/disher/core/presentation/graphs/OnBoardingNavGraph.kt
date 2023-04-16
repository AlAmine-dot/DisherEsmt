package com.example.esmt.cours.disher.core.presentation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.esmt.cours.disher.core.presentation.onboarding.OnBoardingContent

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