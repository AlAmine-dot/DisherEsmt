package com.example.esmt.cours.disher.core.presentation.graphs

import android.util.Log
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.esmt.cours.disher.feature_meals.presentation.Mainsearch.main_screen.MainSearchScreen
import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeScreen
import com.example.esmt.cours.disher.feature_meals.presentation.meal_details.MealDetailsScreen
import com.example.esmt.cours.disher.feature_meals.presentation.search.FavScreen
import com.example.esmt.cours.disher.feature_meals.presentation.search.overview_screen.SearchScreen
import com.plcoding.mvvmtodoapp.util.UiEvent


@Composable
fun MainNavGraph(navController: NavHostController,snackbarHostState: SnackbarHostState,sendMainUiEvent: (UiEvent) -> Unit) {
    NavHost(
        navController = navController,
        route = Graph.HOME.route,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route){
            HomeScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                onPopBackStack = {
                    navController.popBackStack()
                },
                onShowMealDetailsScreen = { uiEvent ->
                    val id = uiEvent.id
                    Log.d("argsmealid", "step-out 1: $id")
                    navController.navigate(MealDetailsScreen.Details.passMealId(id))
                },
                sendMainUiEvent = { uiEvent ->
                    sendMainUiEvent(uiEvent)
                }
            )
        }
//        composable(route = BottomBarScreen.Search.route){
//            SearchScreen(
//                onNavigate = {
//                    navController.navigate(it.route)
//                },
//                onPopBackStack = {
//                    navController.popBackStack()
//                },
//                sendMainUiEvent = { uiEvent ->
//                    sendMainUiEvent(uiEvent)
//                },
//                onShowMealDetailsScreen = { uiEvent ->
//                    val id = uiEvent.id
//                    Log.d("argsmealid", "step-out 1: $id")
//                    navController.navigate(MealDetailsScreen.Details.passMealId(id))
//                }
//            )
//        }
        composable(route = BottomBarScreen.Cart.route){
            FavScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                onPopBackStack = {
                    navController.popBackStack()
                },
                sendMainUiEvent = { uiEvent ->
                    sendMainUiEvent(uiEvent)
                },onShowMealDetailsScreen = { uiEvent ->
                    val id = uiEvent.id
                    Log.d("argsmealid", "step-out 1: $id")
                    navController.navigate(MealDetailsScreen.Details.passMealId(id))
                }
            )
        }
        composable(route = MealDetailsScreen.Details.route + "?mealId={mealId}",
            arguments = listOf(
                navArgument(name = "mealId"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
            ){
            Log.d("argsmealId", "composable level" + it.arguments?.getInt("mealId").toString())
            val mealId = it.arguments?.getInt("mealId") ?: -1
            MealDetailsScreen(
                mealId = mealId,
                onNavigate = {
                    navController.navigate(it.route)
                },
                onPopBackStack = {
                    navController.popBackStack()
                },
                sendMainUiEvent = { uiEvent ->
                    sendMainUiEvent(uiEvent)
                },
            )
        }
        searchNavGraph(navController = navController,sendMainUiEvent)
    }
}

fun NavGraphBuilder.searchNavGraph(navController: NavHostController,sendMainUiEvent: (UiEvent) -> Unit) {
    navigation(
        route = Graph.SEARCH.route,
        startDestination = BottomBarScreen.Search.route,
    ) {
        composable(
            route = BottomBarScreen.Search.route){
            MainSearchScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                onShowOverview = { event ->
                    val query = event.query
                    navController.navigate(SearchScreen.Overview.passQueryString(query))
                },
                onPopBackStack = {
                    navController.popBackStack()
                },
                sendMainUiEvent = { uiEvent ->
                    sendMainUiEvent(uiEvent)
                }
            )
        }
        composable(
            route = SearchScreen.Overview.route,
            arguments = listOf(navArgument("query"){
                type = NavType.StringType
            })
        ){
            val query = it.arguments?.getString("query").toString()
            Log.d("testRedirectSearch",query)

            SearchScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                onPopBackStack = {
                    navController.popBackStack()
                },
                sendMainUiEvent = { uiEvent ->
                    sendMainUiEvent(uiEvent)
                },
                onShowMealDetailsScreen = { uiEvent ->
                    val id = uiEvent.id
                    Log.d("argsmealid", "step-out 1: $id")
                    navController.navigate(MealDetailsScreen.Details.passMealId(id))
                },
                query = query
            )
        }
    }
}

sealed class SearchScreen(val route: String) {
//    object Main : SearchScreen(route = "search_main_screen")
    object Overview : SearchScreen(route = "search_overview_screen/{query}"){
        fun passQueryString(query: String): String{
            return "search_overview_screen/${query}"
        }
    }
}


sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "HOME",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Search : BottomBarScreen(
        route = "SEARCH",
        title = "Search",
        icon = Icons.Default.Search
    )

    object Cart : BottomBarScreen(
        route = "FAVORITES",
        title = "Favorites",
        icon = Icons.Default.Favorite
    )
}

sealed class MealDetailsScreen(
    val route: String,
    val title: String
){
    object Details : MealDetailsScreen(
        route = "meal_details",
        title = "DETAILS"
    ){
        fun passMealId(id: Int): String {
            return "meal_details?mealId=$id"
        }
    }
}