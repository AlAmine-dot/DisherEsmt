package com.example.esmt.cours.disher.ui.customized_items

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.core.presentation.graphs.BottomBarScreen
import com.example.esmt.cours.disher.core.presentation.graphs.MealDetailsScreen
import com.example.esmt.cours.disher.core.presentation.graphs.SearchScreen
import com.example.esmt.cours.disher.ui.theme.LightTurquoise
import com.example.esmt.cours.disher.ui.theme.MeltyGreen
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.schedule

@Composable
fun TopAppBar2(
    navController: NavHostController,
    isVisible: Boolean,
    onToggleVisibility: (Boolean) -> Unit
){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val noTopBarScreens = listOf(
        MealDetailsScreen.Details.route.substringBefore("?"),
        SearchScreen.CategoryDetails.route.substringBefore("/"),
        SearchScreen.Overview.route.substringBefore("/")
    )

    val topBarScreenDestination = currentDestination?.route?.substringBefore("?")?.substringBefore("/") !in noTopBarScreens

    if(!topBarScreenDestination){
        Timer().schedule(1000) {
        onToggleVisibility(false)
        }
    }else{
        Timer().schedule(1000) {
            onToggleVisibility(true)
        }
    }

//    if(isVisible) {
        AnimatedVisibility(
            visible = topBarScreenDestination,
            enter = slideInVertically(
                // Enters by sliding down from offset -fullHeight to 0.
                initialOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 50, easing = LinearOutSlowInEasing)
            ),
            exit = slideOutVertically(
                // Exits by sliding up from offset 0 to -fullHeight.
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 350, easing = FastOutLinearInEasing)
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                elevation = 10.dp,
                color = Color.White
            ) {
                TopAppBar(
                    elevation = 16.dp,
                    backgroundColor = Color.White
                ) {
                    when (currentDestination?.route) {
                        BottomBarScreen.Home.route -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.Center),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_disher_white),
                                        contentDescription = "",
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(60.dp)
                                    )
                                    Text(
                                        text = "Disher",
                                        color = MeltyGreen,
                                        style = MaterialTheme.typography.h3,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 30.sp
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = "",
                                    tint = LightTurquoise,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 5.dp)
                                )
                            }
                        }
                        BottomBarScreen.Search.route -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.Center),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = currentDestination?.route.orEmpty()
                                            .toUpperCase(Locale.ROOT),
                                        color = MeltyGreen,
                                        style = MaterialTheme.typography.h3,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 30.sp,
                                        modifier = Modifier
                                            .padding(start = 10.dp)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = "",
                                    tint = LightTurquoise,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 5.dp)
                                )
                            }
                        }
                        BottomBarScreen.Cart.route -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.Center),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = currentDestination?.route.orEmpty()
                                            .toUpperCase(Locale.ROOT),
                                        color = MeltyGreen,
                                        style = MaterialTheme.typography.h3,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 30.sp,
                                        modifier = Modifier
                                            .padding(start = 10.dp)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = "",
                                    tint = LightTurquoise,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 5.dp)
                                )
                            }
                        }
                    }


                }
            }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview(){
    TopAppBar2(navController = rememberNavController(),true,{})
}