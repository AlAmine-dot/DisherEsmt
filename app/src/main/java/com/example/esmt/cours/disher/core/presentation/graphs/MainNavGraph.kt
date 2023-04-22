package com.example.esmt.cours.disher.core.presentation.graphs

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.esmt.cours.disher.feature_meals.presentation.Mainsearch.main_screen.MainSearchScreen
import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeScreen
import com.example.esmt.cours.disher.feature_meals.presentation.meal_details.details_screen.MealDetailsScreen
import com.example.esmt.cours.disher.feature_meals.presentation.search.FavScreen
import com.example.esmt.cours.disher.feature_meals.presentation.search.category_details_screen.CategoryScreen
import com.example.esmt.cours.disher.feature_meals.presentation.search.overview_screen.SearchScreen
import com.example.esmt.cours.disher.core.presentation.main_screen.UiEvent
import com.example.esmt.cours.disher.feature_meals.presentation.meal_details.youtube_viewer_screen.YtViewerScreen
import com.example.esmt.cours.disher.feature_meals.presentation.search.CartScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
//import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.composable

val fadeDuration = 800

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavGraph(navController: NavHostController,sendMainUiEvent: (UiEvent) -> Unit) {

    AnimatedNavHost(
        navController = navController,
        route = Graph.HOME.route,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = fadeDuration))
            },
            exitTransition = {
                Log.d("testIS",targetState.destination.route.orEmpty())
                when(targetState.destination.route?.substringBefore("?")){
                    MealDetailsScreen.Details.route.substringBefore("?") ->{
                        fadeOut(animationSpec = tween(durationMillis = fadeDuration + 5000))
                    }
                    else -> {
                        fadeOut(animationSpec = tween(durationMillis = fadeDuration))
                    }
                }
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = fadeDuration))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = fadeDuration))
            }
            ){
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

        composable(route = BottomBarScreen.Favorites.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = fadeDuration))
            },
            exitTransition = {
                when(targetState.destination.route?.substringBefore("?")){
                    MealDetailsScreen.Details.route.substringBefore("?") ->{
                        fadeOut(animationSpec = tween(durationMillis = 5000))
                    }
                    else -> {
                        fadeOut(animationSpec = tween(durationMillis = fadeDuration))
                    }
                }
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = fadeDuration))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = fadeDuration))
            }
            ){
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

        composable(route = BottomBarScreen.Cart.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = fadeDuration))
            },
            exitTransition = {
                when(targetState.destination.route?.substringBefore("?")){
                    MealDetailsScreen.Details.route.substringBefore("?") ->{
                        fadeOut(animationSpec = tween(durationMillis = 5000))
                    }
                    else -> {
                        fadeOut(animationSpec = tween(durationMillis = fadeDuration))
                    }
                }
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = fadeDuration))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = fadeDuration))
            }
        ){
            CartScreen(
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
            enterTransition = {
                slideInVertically (
                    initialOffsetY = { 600 },
                    animationSpec = tween(700, easing = EaseIn)
                ) +
                fadeIn(animationSpec = tween(1100))
            },
            exitTransition = {
                slideOutVertically (
                    targetOffsetY = { 600 },
                    animationSpec = tween(700, easing = EaseIn)
                ) +
                fadeOut(animationSpec = tween(500,100))
            },
            popEnterTransition = {
                slideInVertically (
                    initialOffsetY = { 600 },
                    animationSpec = tween(700, easing = EaseIn)
                ) +
                        fadeIn(animationSpec = tween(1100))
            },
            popExitTransition = {
                slideOutVertically (
                    targetOffsetY = { 600 },
                    animationSpec = tween(700, easing = EaseIn)
                ) +
                        fadeOut(animationSpec = tween(500,100))
            },
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
                onShowMealDetailsVideo = { uiEvent ->
                    val videoUrl = uiEvent.videoUrl
                    Log.d("testVideoUrl",videoUrl)
                    navController.navigate(MealDetailsScreen.YtViewer.passVideoUrl(videoUrl))
                },
            )
//            TestScreen2()
        }
        composable(
            route = MealDetailsScreen.YtViewer.route + "?videoUrl={videoUrl}",
            arguments = listOf(navArgument(name = "videoUrl"){
                type = NavType.StringType
                defaultValue = ""
            }
            )){

            val videoUrl = it.arguments?.getString("videoUrl").toString()
            Log.d("testRedirectSearchVideo",videoUrl)

            YtViewerScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                onPopBackStack = {
                    navController.popBackStack()
                },
                sendMainUiEvent = { uiEvent ->
                    sendMainUiEvent(uiEvent)
                },
                ytVideoUrl = videoUrl
            )
        }
        searchNavGraph(navController = navController,sendMainUiEvent)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.searchNavGraph(navController: NavHostController, sendMainUiEvent: (UiEvent) -> Unit) {
    navigation(
        route = Graph.SEARCH.route,
        startDestination = BottomBarScreen.Search.route,
    ) {
        composable(
            route = BottomBarScreen.Search.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = fadeDuration))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = fadeDuration))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = fadeDuration))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = fadeDuration))
            }
            ){
            MainSearchScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                onShowOverview = { event ->
                    val query = event.query
                    navController.navigate(SearchScreen.Overview.passQueryString(query))
                },
                onShowCategoryDetails = { event ->
                    val categoryId = event.idCategory
                    navController.navigate(SearchScreen.CategoryDetails.passCategoryId(categoryId))
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
        composable(
            route = SearchScreen.CategoryDetails.route,
            arguments = listOf(navArgument("categoryId"){
                type = NavType.IntType
            })
        ){
            val categoryId = it.arguments?.getInt("categoryId")
            Log.d("testRedirectCategory", categoryId.toString())

            if (categoryId != null) {
                CategoryScreen(
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
                    categoryId = categoryId
                )
            }
        }
    }
}

sealed class SearchScreen(val route: String) {
    object CategoryDetails : SearchScreen(route = "search_category_screen/{categoryId}"){
        fun passCategoryId(id: Int): String{
            return "search_category_screen/${id}"
        }
    }
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

    object Favorites : BottomBarScreen(
        route = "FAVORITES",
        title = "Favorites",
        icon = Icons.Default.Favorite
    )

    object Cart : BottomBarScreen(
        route = "CART",
        title = "Cart",
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

    object YtViewer : MealDetailsScreen(

        route = "meal_video",
        title = "MEAL_VIDEO"
    ){
        fun passVideoUrl(videoUrl: String): String {
            return "meal_video?videoUrl=$videoUrl"
        }
    }
}