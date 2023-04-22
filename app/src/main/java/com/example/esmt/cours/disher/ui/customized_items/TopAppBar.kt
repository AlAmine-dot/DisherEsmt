package com.example.esmt.cours.disher.ui.customized_items

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.core.presentation.graphs.BottomBarScreen
import com.example.esmt.cours.disher.core.presentation.graphs.MealDetailsScreen
import com.example.esmt.cours.disher.ui.theme.LightTurquoise
import com.example.esmt.cours.disher.ui.theme.MeltyGreen
import java.util.Locale

@Composable
fun TopAppBar2(
    topBarContent: TopBarContent,
    isVisible: Boolean,
    onPopBackStack: () -> Unit = {},
){
    Log.d("testTBState", isVisible.toString())
    val currentRoute = topBarContent.route

        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                // Enters by sliding down from offset -fullHeight to 0.
                initialOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
            ),
            exit = slideOutVertically(
                // Exits by sliding up from offset 0 to -fullHeight.
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 100, easing = FastOutLinearInEasing)
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                elevation = 10.dp,
                color = if(currentRoute == MealDetailsScreen.Details.route.substringBefore("?")) {
                    MeltyGreen
                }else{
                    Color.White
                }
            ) {
                TopAppBar(
                    elevation = 16.dp,
                    backgroundColor = if(currentRoute == MealDetailsScreen.Details.route.substringBefore("?")) {
                        MeltyGreen
                    }else{
                        Color.White
                    }
                ) {
                    when (currentRoute) {
                        MealDetailsScreen.Details.route.substringBefore("?") -> {
                            val mealName = topBarContent.getArgByKey("mealName")?.value.toString()
                            val isMealFavorite = topBarContent.getArgByKey("isFavorite")?.value as Boolean

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth().padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Go back",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .align(Alignment.CenterStart)
                                        .padding(start = 5.dp)
                                        .clickable { onPopBackStack() }
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.Center),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = mealName,
                                        color = Color.White,
                                        style = MaterialTheme.typography.h3,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 20.sp
                                    )

                                }
                                Icon(
                                    imageVector = if(isMealFavorite) {
                                        Icons.Default.Favorite
                                    }else{
                                         Icons.Outlined.FavoriteBorder
                                    },
                                    contentDescription = "Favorite meal button",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 5.dp)
                                )
                            }
                        }
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
                                        text = currentRoute
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
                        BottomBarScreen.Favorites.route -> {
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
                                        text = currentRoute
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
                                        text = currentRoute
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
//    TopAppBar2(navController = rememberNavController(),true,{})
}

data class TopBarContent(val route: String, val args: List<TopBarArgument>){
    fun getArgByKey(argKey: String): TopBarArgument?{
        return args.find { it.key == argKey }
    }
}
data class TopBarArgument(val key: String, val value: Any)