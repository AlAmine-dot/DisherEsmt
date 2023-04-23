package com.example.esmt.cours.disher.core.presentation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.animation.composable
//import androidx.navigation.compose.composable
import com.example.esmt.cours.disher.core.presentation.main_screen.MainScreen
import com.example.esmt.cours.disher.ui.TestScreen
import com.example.esmt.cours.disher.ui.TestScreen2
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.navigation
import com.example.esmt.cours.disher.core.presentation.onboarding.OnBoardingContent
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@OptIn(ExperimentalAnimationApi::class)
@Composable

fun RootNavigationGraph(navController: NavHostController,
                        startDestination: String) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Graph.ONBOARDING.route){
            OnBoardingContent(navController = navController)

        }
        composable(route = Graph.HOME.route){
            MainScreen()
//            TestScreen2()
//            HomeScreen()
        }
    }
}

sealed class Graph(val route: String) {
    object ROOT: Graph(route = "root_graph")
    // Je sais pas si on va garder celui là en graph ou en page singleton :
    object ONBOARDING: Graph(route = "onboarding_graph")
    object HOME: Graph(route = "home_graph")
    object SEARCH: Graph(route = "search_graph")
//    object DETAILS: Graph(route = "details_graph?id={id}"){
//        fun passId(id: Int): String {
//            return "details_graph?id=$id"
//        }
//    }
}