package com.example.esmt.cours.disher.core.presentation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.esmt.cours.disher.core.presentation.main_screen.MainScreen
import com.example.esmt.cours.disher.core.presentation.onboarding.OnBoardingContent
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Graph.ONBOARDING.route){
            OnBoardingContent(navController = navController)

        }
        composable(route = Graph.HOME.route){
            MainScreen()
//            HomeScreen()
        }
    }
}

sealed class Graph(val route: String) {
    object ROOT: Graph(route = "root_graph")
    // Je sais pas si on va garder celui là en graph ou en page singleton :
    object ONBOARDING: Graph(route = "onboarding_graph")
    object HOME: Graph(route = "home_graph")
//    object DETAILS: Graph(route = "details_graph?id={id}"){
//        fun passId(id: Int): String {
//            return "details_graph?id=$id"
//        }
//    }
}