package com.example.esmt.cours.disher.ui.customized_items

import android.view.Surface
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.esmt.cours.disher.core.presentation.graphs.BottomBarScreen
import com.example.esmt.cours.disher.ui.theme.DarkTurquoise
import com.example.esmt.cours.disher.ui.theme.MeltyGreen
import com.example.esmt.cours.disher.ui.theme.MeltyGreenLO
import com.example.esmt.cours.disher.ui.theme.TextWhite

@Composable
fun NavBar2(
    screens: List<BottomBarScreen>,
    navController: NavHostController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    AnimatedVisibility(
        visible = bottomBarDestination,
        enter = slideInVertically{56},
        exit = slideOutVertically {56},
//        modifier = Modifier.
    ) {
        Surface(
            shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            elevation = 16.dp,
            color = Color.White
        ) {


            Row(Modifier.fillMaxSize()) {

                screens.forEachIndexed { index, screen ->
                    val isSelected =
                        currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        contentAlignment = Center
                    ) {

                        Box(
                            Modifier
                                .size(40.dp)
                                .clickable {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                }
                                .background(
                                    if (isSelected) MeltyGreen
                                    else Color.Transparent,
                                    RoundedCornerShape(18.dp)

                                ),
                            contentAlignment = Center
                        ) {

                            Icon(
                                imageVector = screen.icon,
                                null,
                                Modifier.size(24.dp),
                                tint = if (isSelected) Color.White else DarkTurquoise.copy(alpha = .6f)
                            )

                        }

                    }

                }

            }

        }
    }

}