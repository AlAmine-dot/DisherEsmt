package com.example.esmt.cours.disher.core.presentation.main_screen

import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.esmt.cours.disher.core.common.ConnectivityObserver
import com.example.esmt.cours.disher.core.presentation.graphs.BottomBarScreen
import com.example.esmt.cours.disher.core.presentation.graphs.MainNavGraph
import com.example.esmt.cours.disher.ui.customized_items.NavBar2
import com.example.esmt.cours.disher.ui.theme.*
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    navController: NavHostController = rememberAnimatedNavController()
) {


    val snackbarHostState = remember {
        SnackbarHostState()
    }


    val scope = rememberCoroutineScope()
    val mainUiState by viewModel.uiState.collectAsState()

    val status by mainUiState.connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Available
    )


    LaunchedEffect(key1 = true) {

        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        event.message,
                        event.action,
                    )
                }
                is UiEvent.HideSnackbar -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        bottomBar = { BottomBar(navController = navController, status = status) },
        snackbarHost = {snackbarHostState},
        topBar = {
//            TopBar(navController = navController,mainUiState.isBottomBarVisible,{ bool -> viewModel.toggleBottomBarVisibility(bool)})
        },
        modifier = Modifier.padding(top = 0.dp)
    ) { paddingValues -> Log.d("args", paddingValues.toString())


        MainNavGraph(navController = navController) { uiEvent ->
            // Cette solution ne me satisfait vraiment pas. Nous trouverons mieux plus tard
            snackbarHostState.currentSnackbarData?.dismiss()
            viewModel.sendMainUiEvent(uiEvent)
        }



    }

    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                content = {
                    Text(
                        text = data.message,
                        color = DarkTurquoise,
                        fontSize = 16.sp
                    )
                },
                action = {
                    Column(Modifier.height(70.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                        Icon(
                            imageVector = Icons.Default.Close,
                            tint= DarkTurquoise,
                            contentDescription = "Close icon",
                            modifier = Modifier
                                .clickable { scope.launch { snackbarHostState.currentSnackbarData?.dismiss() } }

                        )
                    }
                },
                backgroundColor = LightInfoBg,
                elevation = 8.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 680.dp)
                    .defaultMinSize(minHeight = 70.dp)
            )
        }
    )
}

@Composable
fun BottomBar(navController: NavHostController,status: ConnectivityObserver.Status) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Search,
        BottomBarScreen.Favorites,
        BottomBarScreen.Cart
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        NavBar2(screens = screens, navController = navController)


        androidx.compose.animation.AnimatedVisibility(
            visible = status != ConnectivityObserver.Status.Available ,
            enter = slideInVertically { 22 },
            exit = slideOutVertically { 22 },
//        modifier = Modifier.
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(22.dp)
                    .background(MeltyGreen),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (status) {
                        ConnectivityObserver.Status.Available -> "Available"
                        ConnectivityObserver.Status.Losing -> "Losing network connection..."
                        else -> {
                            "Connection lost, please check your network."
                        }
                    },
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

}

@Composable
fun TopBar(navController: NavHostController,isVisible: Boolean,onToggleVisibility: (Boolean) -> Unit) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

//    if()
//    TopAppBar2(navController = navController, isVisible, onToggleVisibility)

//    TopAppBar(
//        title = {
//            Icon(painterResource(id = R.drawable.ic_disher_white), contentDescription = "", tint = Color.Unspecified)
//            Text(text = "DISHER", color= MeltyGreen)
//        },
//        elevation = 16.dp,
//        backgroundColor = Color.White
//    )
}


//@Composable
//fun BottomBar(navController: NavHostController) {
//    val screens = listOf(
//        BottomBarScreen.Home,
//        BottomBarScreen.Search,
//        BottomBarScreen.Cart,
//
//        )
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentDestination = navBackStackEntry?.destination
//
//    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
//    if (bottomBarDestination) {
//        BottomNavigation {
//            screens.forEach { screen ->
//                AddItem(
//                    screen = screen,
//                    currentDestination = currentDestination,
//                    navController = navController
//                )
//            }
//        }
//    }
//}



@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    // Par ici aussi, nous en viendrons aux détails demain :
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}