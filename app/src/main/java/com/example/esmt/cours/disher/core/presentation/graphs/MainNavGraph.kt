package com.example.esmt.cours.disher.core.presentation.graphs

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeScreen
import com.example.esmt.cours.disher.feature_meals.presentation.meal_details.MealDetailsScreen
import com.example.esmt.cours.disher.feature_meals.presentation.search.CartScreen
import com.example.esmt.cours.disher.feature_meals.presentation.search.SearchScreen


@Composable
fun MainNavGraph(navController: NavHostController) {
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
                }
            )
        }
        composable(route = BottomBarScreen.Search.route){
            SearchScreen()
        }
        composable(route = BottomBarScreen.Cart.route){
            CartScreen()
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
            )
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
        title = "HOME",
        icon = Icons.Default.Home
    )

    object Search : BottomBarScreen(
        route = "SEARCH",
        title = "SEARCH",
        icon = Icons.Default.Search
    )

    object Cart : BottomBarScreen(
        route = "CART",
        title = "CART",
        icon = Icons.Default.ShoppingCart
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