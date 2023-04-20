package com.example.esmt.cours.disher.core.presentation.main_screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.core.presentation.graphs.BottomBarScreen
import com.example.esmt.cours.disher.core.presentation.graphs.MainNavGraph
import com.example.esmt.cours.disher.ui.customized_items.NavBar2
import com.example.esmt.cours.disher.ui.customized_items.TopAppBar2
import com.example.esmt.cours.disher.ui.theme.*
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }


    val scope = rememberCoroutineScope()
    val mainUiState by viewModel.uiState.collectAsState()


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
//
//                    if (event.message != lastSnackbarMessage) {
//                        // Afficher le Snackbar avec un message et un action.
//                        snackbarHostState.showSnackbar(
//                            message = event.message,
//                            actionLabel = event.action,
//                            duration = SnackbarDuration.Long
//                        )
//
//                        // Mettre à jour le dernier message affiché.
//                        lastSnackbarMessage = event.message
//                    }
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
        bottomBar = { BottomBar(navController = navController) },
        snackbarHost = {snackbarHostState},
        topBar = { TopBar(navController = navController,mainUiState.isBottomBarVisible,{ bool -> viewModel.toggleBottomBarVisibility(bool)}) },
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
//            Spacer(Modifier.fillMaxHeight(0f))
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
                    .padding(top = 0.dp)
                    .defaultMinSize(minHeight = 70.dp)
            )
        }
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Search,
        BottomBarScreen.Cart,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavBar2(screens = screens, navController = navController)

}

@Composable
fun TopBar(navController: NavHostController,isVisible: Boolean,onToggleVisibility: (Boolean) -> Unit) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

//    if()
    TopAppBar2(navController = navController, isVisible, onToggleVisibility)

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