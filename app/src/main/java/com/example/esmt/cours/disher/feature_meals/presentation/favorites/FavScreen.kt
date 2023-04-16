package com.example.esmt.cours.disher.feature_meals.presentation.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.core.presentation.graphs.BottomBarScreen
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.favorites.FavUiEvent
import com.example.esmt.cours.disher.feature_meals.presentation.favorites.FavViewModel
import com.example.esmt.cours.disher.ui.theme.*
import com.plcoding.mvvmtodoapp.util.UiEvent

@Composable
fun FavScreen(
    onNavigate: (FavUiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    favViewModel: FavViewModel = hiltViewModel(),
    sendMainUiEvent: (UiEvent) -> Unit,
    onShowMealDetailsScreen: (FavUiEvent.ShowMealDetails) -> Unit,
    ){

    val favUiState by favViewModel.uiState.collectAsState()
    val favoriteMeals = favUiState.favoriteMeals

    LaunchedEffect(key1 = true) {

        favViewModel.uiEvent.collect { event ->
            when (event) {
                is FavUiEvent.ShowSnackbar -> {
                    sendMainUiEvent(UiEvent.HideSnackbar)
                    sendMainUiEvent(UiEvent.ShowSnackbar(event.message, event.action))
                }
                is FavUiEvent.Navigate -> {

                }
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
        ,
    ) {
        TopCardComponent()
        MealsListComponent(
            mealItems = favoriteMeals,
            onNavigate = onNavigate,
            onMealClicked = { mealId ->
                Log.d("argsmealId", "Reached level 1")
                onShowMealDetailsScreen(FavUiEvent.ShowMealDetails(mealId))
            },
            onDeleteClicked = { meal ->
                favViewModel.onEvent(FavUiEvent.RemoveMealFromFavorites(meal))
            }
        )
    }
}

@Composable
fun MealsListComponent(
    mealItems: List<Meal>,
    onNavigate: (FavUiEvent.Navigate) -> Unit,
    onMealClicked : (mealId: Int) -> Unit,
    onDeleteClicked: (meal: Meal) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text =  "${mealItems.size} recipes",
                style = MaterialTheme.typography.h5,
                color = LightTurquoise,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                modifier = Modifier
                    .widthIn(180.dp),
                onClick = { onNavigate(FavUiEvent.Navigate(BottomBarScreen.Home.route)) },
                shape = RoundedCornerShape(70.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MeltyGreen),
            ) {
                Text(
                    text = "Add recipes",
                    color = Color.White,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add recipe icon",
                    tint = Color.White
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
                modifier = Modifier.fillMaxHeight(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(mealItems.size) {
                    MealItem(meal = mealItems[it], onMealClicked, onDeleteClicked)
                }
            }
        }
    }

}

@Composable
fun MealItem(
    meal: Meal,
    onMealClicked: (mealId: Int) -> Unit,
    onDeleteClicked: (meal: Meal) -> Unit
){
    Column(
        modifier = Modifier
            .height(226.dp)
            .width(200.dp),

        ){

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.89f)
                .clickable { onMealClicked(meal.id) },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White,
            elevation = 10.dp
        ) {
            val painter = rememberImagePainter(
                data = meal.strMealThumb,
                builder = {
                    crossfade(durationMillis = 1200)
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_placeholder)
                }
            )
            Image(
                painter = painter,
                contentDescription = "Image ${meal.strMealName}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 200f
                    )
                )
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = meal.strArea.orEmpty(),
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MeltyGreen)
                            .padding(vertical = 4.dp, horizontal = 14.dp)
                            .alpha(0.7f),

                        )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(53.dp)
                        .padding(10.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFCE4F4B))
                        .align(Alignment.TopEnd)
                        .padding(0.dp)
                        .clickable {
                            onDeleteClicked(meal)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete meal from favorites",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp).padding(0.dp)
                    )
                }
//                Spacer(Modifier.weight(9f))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .fillMaxHeight(0.25f),
                    contentAlignment = Alignment.CenterStart
                ){
                    Text(
                        modifier = Modifier
                            .padding(top = 0.dp, start = 10.dp),
                        text= meal.strMealName.orEmpty(),
                        color = TextWhite,
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier.padding(start = 3.dp),
            ){
                Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, contentDescription = null)
                Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, contentDescription = null)
                Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, contentDescription = null)
                Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, contentDescription = null)
                Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, contentDescription = null)
            }

//            Spacer(Modifier.weight(1f))

            Text(
                modifier = Modifier.padding(end = 10.dp),
                text="4.6/5",
                color = LightTurquoise,
            )
        }
    }
}
@Composable
fun TopCardComponent(){
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(14.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MeltyGreenLO)
            .border(2.dp, MeltyGreen, RoundedCornerShape(20.dp))
            .padding(7.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier
                .widthIn(min = 75.dp)
                .padding(vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy((-4).dp)
        ){
            Card(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .padding(7.dp),
                elevation = 4.dp,
                shape = CircleShape
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Row(
//                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                        ,
                        horizontalArrangement = Arrangement.spacedBy(0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Location",
                            tint = MeltyGreen,
                            modifier = Modifier.size(20.dp),
                        )
                        Text(
                            text="1",
                            style = MaterialTheme.typography.h6,
                            color = MeltyGreen,
                            modifier = Modifier
                                .padding(bottom = 2.dp),
//                                .align(Alignment.CenterStart)
                        )
                    }
                }
            }
            Text(
                text = "Dishes",
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp
            )

        }

        Column(
            modifier = Modifier.widthIn(min = 75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy((-4).dp)
        ){
            Card(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .padding(7.dp),
                elevation = 4.dp,
                shape = CircleShape
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "Location",
                        tint = MeltyGreen,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
            Text(
                text = "Taste",
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp
            )

        }

        Column(
            modifier = Modifier.widthIn(min = 75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy((-4).dp)
        ){
            Card(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .padding(7.dp),
                elevation = 4.dp,
                shape = CircleShape
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Location",
                        tint = MeltyGreen,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
            Text(
                text = "Diet",
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp
            )

        }

        Column(
            modifier = Modifier.widthIn(min = 75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy((-4).dp)
        ){
            Card(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .padding(7.dp),
                elevation = 4.dp,
                shape = CircleShape
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = "Location",
                        tint = MeltyGreen,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
            Text(
                text = "Equipment",
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp
            )

        }



    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview(){
    FavScreen({},{}, viewModel(),{},{})
}