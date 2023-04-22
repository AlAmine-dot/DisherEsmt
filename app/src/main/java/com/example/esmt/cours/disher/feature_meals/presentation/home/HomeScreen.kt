package com.example.esmt.cours.disher.feature_meals.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberImagePainter
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.core.presentation.graphs.BottomBarScreen
import com.example.esmt.cours.disher.core.presentation.graphs.SearchScreen
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.CategoryFeature
import com.example.esmt.cours.disher.ui.theme.*
import com.example.esmt.cours.disher.core.presentation.main_screen.UiEvent
import com.example.esmt.cours.disher.ui.customized_items.RadioToggler
import com.example.esmt.cours.disher.ui.customized_items.TopAppBar2
import com.example.esmt.cours.disher.ui.customized_items.TopBarContent

@Composable
fun HomeScreen(
    onNavigate: (HomeUiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    onShowMealDetailsScreen: (HomeUiEvent.ShowMealDetails) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    sendMainUiEvent: (UiEvent) -> Unit,
){

        val homeUiState by homeViewModel.uiState.collectAsState()
        val categoryFeatures = homeUiState.getCategoryFeatures()

        val trigger by remember { mutableStateOf(homeUiState.feedModeOption == FeedMode.DISCOVERY) }

        Scaffold(
            topBar = {TopAppBar2(TopBarContent(BottomBarScreen.Home.route, emptyList()),true,{})}
        ) {paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(TextWhite)
                    .fillMaxSize()
                    .padding(start = 0.dp, bottom = 39.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(25.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioToggler(
                            item1 = "Discovery mode",
                            item2 = "Custom mode",
                            trigger = homeUiState.feedModeOption == FeedMode.DISCOVERY,
                            onClickItem1 = {
                                homeViewModel.onEvent(HomeUiEvent.OnToggleFeedMode(FeedMode.DISCOVERY))
                            },
                            onClickItem2 = {
                                homeViewModel.onEvent(HomeUiEvent.OnToggleFeedMode(FeedMode.CUSTOM))
                            }
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 0.dp)
                        ,
                        horizontalArrangement = Arrangement.Center
                    ){
                        AskCardComponent(onNavigate)
                    }
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

}

@Composable
fun AskCardComponent(
    onNavigate: (HomeUiEvent.Navigate) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth(.90f)
            .width(300.dp)
            .height(200.dp)
        ,
        backgroundColor = MeltyGreenLO,
        elevation = 16.dp,
        shape = RoundedCornerShape(18.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier

                    .fillMaxHeight()
                    .weight(.5f)
                    .padding(start = 10.dp)
                ,
                verticalArrangement = Arrangement.Center,
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                ) {
                    Text(
                        text = "Do you like fast food ?",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        color = DarkTurquoise
                    )
                    Button(
                        colors = ButtonDefaults.buttonColors(DarkTurquoise),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            // On redirige vers la catégorie "Beef", il serait plus pertinent de ne pas hard-coder
                            // l'id de la catégorie, je rectifierai ça après, le temps manque
                            onNavigate(HomeUiEvent.Navigate(SearchScreen.CategoryDetails.passCategoryId(1)))
                        }
                    ) {
                        Text(
                            text = "Of course",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(.5f)
                    .fillMaxHeight()
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(id = R.drawable.ic_fastfood),
                    contentDescription = "Burger image")
            }
        }

    }
}

@Composable
fun CategoryFeature(feature: CategoryFeature, onMealClicked: (id: Int) -> Unit) {

    Text(
        text= feature.featureTitle,
        color= DarkTurquoise,
        style = MaterialTheme.typography.h1,
        modifier = Modifier.padding(bottom = 15.dp, start = 18.dp)
    )

    LazyRow(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(15.dp)) {
        item {
            Spacer(modifier = Modifier.width(0.dp))
        }
        items(feature.featuredMeals) { item ->
            MealCard(meal = item, onClick = { id ->
                Log.d("argsmealId", "Reached level 2")
                onMealClicked(id)})
        }
        item {
            Spacer(modifier = Modifier.width(0.dp))
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
,

    ){

        Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.89f)
            .clickable {
                Log.d("argsmealId", "Reached level 3")
                onClick(meal.id)
            },
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

                    if(meal.strYoutube?.isNotBlank() == true){

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = .4f))
                                .shadow(100.dp, CircleShape)
                                .border(3.dp, Color.White, CircleShape)
                            ,
                        ){
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play video",
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(30.dp),
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                        .align(Alignment.BottomStart)
                    ,
                    contentAlignment = Alignment.CenterStart
                ){
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp),
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
//    AskCardComponent({})
    val mockMeal = Meal(
        id = 52815,
        dateModified = null,
        strCreativeCommonsConfirmed = null,
        strDrinkAlternate = null,
        strImageSource = null,
        strArea = "French",
        strCategory = "Miscellaneous",
        strInstructions = "Place a large saucepan over medium heat and add oil. When hot, add chopped vegetables and sauté until softened, 5 to 10 minutes.\nAdd 6 cups water, lentils, thyme, bay leaves and salt. Bring to a boil, then reduce to a fast simmer.\nSimmer lentils until they are tender and have absorbed most of the water, 20 to 25 minutes. If necessary, drain any excess water after lentils have cooked. Serve immediately, or allow them to cool and reheat later.\nFor a fuller taste, use some chicken stock and reduce the water by the same amount.",
        strMealName = "French Lentils With Garlic and Thyme",
        strMealThumb = "https://www.themealdb.com/images/media/meals/vwwspt1487394060.jpg",
        strSource = null,
        strTags = "Pulse",
        strYoutube = "https://www.youtube.com/watch?v=CrlTS1mJQMA",
        ingredients = listOf("Olive Oil", "Onion", "Garlic", "Carrot", "French Lentils", "Thyme", "Bay Leaf", "Salt", "Celery", "", "", "", "", "", "", "", "", "", "", ""),
        measures = listOf("3 tablespoons", "1", "2 cloves", "1", "2 1/4 cups", "1 teaspoon", "3", "1 tablespoon", "2 sticks", "", "", "", "", "", "", "", "", "", "", ""),
        isFavorite = false,
        isIntoCart = false
    )
    MealCard(mockMeal,{})
}