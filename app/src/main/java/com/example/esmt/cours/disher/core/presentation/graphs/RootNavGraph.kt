package com.example.esmt.cours.disher.core.presentation.graphs

//import androidx.navigation.compose.composable
//import androidx.compose.animation.ExperimentalAnimationApi
import android.util.Log
import androidx.compose.runtime.Composable
//import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.esmt.cours.disher.core.presentation.main_screen.MainScreen
import com.example.esmt.cours.disher.core.presentation.onboarding.AwaitScreen
import com.example.esmt.cours.disher.core.presentation.onboarding.OnBoardingContent
import com.google.accompanist.navigation.animation.AnimatedNavHost
//import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi

//@ExperimentalAnimationApi
@ExperimentalPagerApi
//@OptIn(ExperimentalAnimationApi::class)
@Composable

fun RootNavigationGraph(navController: NavHostController,
                        startDestination: String) {
    Log.d("startDestInRoot", startDestination)
    NavHost(
        navController = navController,
        startDestination = Graph.AWAIT.route
    ) {
        composable(route = Graph.ONBOARDING.route){
            OnBoardingContent(navController = navController)
        }
        composable(route = Graph.HOME.route){
            MainScreen()
//            TestScreen2()
//            HomeScreen()
        }
        composable(route = Graph.AWAIT.route){

//            if(startDestination == Graph.ONBOARDING.route){
//                OnBoardingContent(navController = navController)
//            }else{
                AwaitScreen({ route ->
                    navController.navigate(route)
                },startDestination)
//            }
        }
    }
}

sealed class Graph(val route: String) {
    object ROOT: Graph(route = "root_graph")
    // Je sais pas si on va garder celui là en graph ou en page singleton :
    object ONBOARDING: Graph(route = "onboarding_graph")
    object AWAIT: Graph(route = "await_graph")
    object HOME: Graph(route = "home_graph")
    object SEARCH: Graph(route = "search_graph")
//    object DETAILS: Graph(route = "details_graph?id={id}"){
//        fun passId(id: Int): String {
//            return "details_graph?id=$id"
//        }
//    }
}