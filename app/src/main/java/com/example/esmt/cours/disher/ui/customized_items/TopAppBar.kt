package com.example.esmt.cours.disher.ui.customized_items

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Person
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
import com.example.esmt.cours.disher.ui.theme.DarkTurquoise
import com.example.esmt.cours.disher.ui.theme.LightTurquoise
import com.example.esmt.cours.disher.ui.theme.MeltyGreen

@Composable
fun TopAppBar2(
    navController: NavHostController
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
//    val screens = listOf<Any>()
//    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        elevation = 16.dp,
        color = Color.White
    ) {
        TopAppBar(
            elevation = 16.dp,
            backgroundColor = Color.White
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                    ,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painterResource(id = R.drawable.ic_disher_white),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(60.dp)
                    )
                    Text(text = "Disher",
                        color= MeltyGreen,
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
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview(){
    TopAppBar2(navController = rememberNavController())
}