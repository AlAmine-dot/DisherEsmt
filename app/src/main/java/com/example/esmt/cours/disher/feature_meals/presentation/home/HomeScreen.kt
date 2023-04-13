package com.example.esmt.cours.disher.feature_meals.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.CategoryFeature
import com.example.esmt.cours.disher.ui.theme.*

@Composable
fun HomeScreen(
    onNavigate: (HomeUiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    onShowMealDetailsScreen: (HomeUiEvent.ShowMealDetails) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
){
        val homeUiState by homeViewModel.uiState.collectAsState()
        val categoryFeatures = homeUiState.getCategoryFeatures()

        LazyColumn(
            modifier = Modifier
                .background(TextWhite)
                .fillMaxSize()
                .padding(start = 15.dp, bottom = 64.dp)

        ) {
            item {
                Spacer(modifier = Modifier.height(60.dp))
            }
            items(categoryFeatures) {feature ->
                CategoryFeature(feature, onMealClicked = { mealId ->
                    Log.d("argsmealId", "Reached level 1")
                    onShowMealDetailsScreen(HomeUiEvent.ShowMealDetails(mealId))
                })
            }
            item{
                Text(homeUiState.error)
            }
        }

}

@Composable
fun CategoryFeature(feature: CategoryFeature, onMealClicked: (id: Int) -> Unit) {

    Text(
        text= feature.featureTitle,
        color= DarkTurquoise,
        style = MaterialTheme.typography.h1,
        modifier = Modifier.padding(vertical = 15.dp)
    )
    LazyRow(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(15.dp)) {
        items(feature.featuredMeals) { item ->
            MealCard(meal = item, onClick = { id ->
                Log.d("argsmealId", "Reached level 2")
                onMealClicked(id)})
        }
    }
}

@Composable
fun MealCard(
    meal: Meal,
    onClick: (id: Int) -> Unit
){
    Column(
        modifier = Modifier
            .height(226.dp)
            .width(200.dp)
            .clickable {
                Log.d("argsmealId", "Reached level 3")
               onClick(meal.id)
            },

    ){

        Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.89f),
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
                    modifier = Modifier.fillMaxSize()
                )
//                AsyncImage(
//                    model = meal.strMealThumb,
//                    placeholder = rememberAsyncImagePainter(
//                        ImageRequest.Builder(LocalContext.current)
//                            .data(data = R.drawable.ic_placeholder).apply(block = fun ImageRequest.Builder.() {
//                                crossfade(durationMillis = 1000)
//                                error(R.drawable.ic_placeholder)
//                                placeholder(R.drawable.ic_placeholder)
//                            }).build()
//                    ),
//                    painterResource(id = R.drawable.ic_placeholder){
//                        crossfade(durationMillis = 1000)
//                    },
//                    contentDescription = "Meal img",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxSize()
//                )

                Column(modifier = Modifier
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
//                            .height(38.dp)
                            .padding(10.dp)
//                            .background(Color.Red)
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
                Spacer(Modifier.weight(0.80f))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.25f),
                    contentAlignment = Alignment.CenterStart
                ){
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp, start = 10.dp),
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
@Preview(showBackground = true)
fun DefaultPreview(){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
//        MealCard()
    }
}