package com.example.esmt.cours.disher.feature_meals.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.ui.theme.*

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
){
//    val uiState = viewModel.state.value

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Column {

                val homeUiState by homeViewModel.uiState.collectAsState()

                MealsList("Trending recipees", homeUiState.meals, homeUiState.isLoading)
                Text(text = homeUiState.error)


        }

    }
}

@Composable
fun MealCard(
    meal: Meal,
){
    Column(
        modifier = Modifier
            .height(300.dp)
            .width(250.dp),
    ){


        Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.85f),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 16.dp
        ) {

                AsyncImage(
                    model = meal.strMealThumb,
                    placeholder = painterResource(id = R.drawable.ic_placeholder),
                    contentDescription = "Meal img",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
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
fun MealsList(
    listTitle: String,
    meals: List<Meal>,
    isLoading: Boolean
){
    Text(
        text=listTitle,
        color= DarkTurquoise,
        style = MaterialTheme.typography.h1,
        modifier = Modifier.padding(15.dp)
    )
    LazyRow(
        modifier = Modifier.fillMaxSize().padding(horizontal = 0.dp),
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),

    ) {
        if(isLoading){
            item {
                CircularProgressIndicator()
            }
        }else {
            itemsIndexed(meals) { index: Int, meal: Meal ->
                val painter = rememberImagePainter(data = meal.strMealThumb, builder = {
                    crossfade(durationMillis = 1000)
                    error(R.drawable.ic_placeholder)
                    placeholder(R.drawable.ic_placeholder)
                })
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(20.dp))
//                        .background(Color(0xFFE8EDFA))
//                        .height(200.dp)
//                        .padding(16.dp)
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .weight(2f)
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .wrapContentSize()
//                                .clip(RoundedCornerShape(24.dp))
//                                .background(Color(209, 213, 225))
//                        ) {
//                            Text(
//                                text = meal.strArea.orEmpty(), modifier = Modifier
//                                    .padding(7.dp)
//                                    .clickable {
//                                    }, style = TextStyle(fontSize = 12.sp)
//                            )
//
//                        }
//                        Spacer(modifier = Modifier.height(4.dp))
//
//                        Text(
//                            text = meal.strMealName.orEmpty(),
//                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
//                        )
//                        Row {
//                            //                    product.rating?.let {
//                            //                        Text( text=product.rating.toString())
//                            //                        repeat(product.rating?.toInt() ?: 0){
//                            //                            Icon(imageVector = Icons.Outlined.Star, tint = Color.Yellow, contentDescription = null)
//                            //                        }
//                            //                    }
//                        }
//                        Spacer(modifier = Modifier.height(4.dp))
//                        OutlinedButton(
//                            onClick = {
//                                //                    product.id?.let{
//                                //                        onProductClick(it)
//                                //                    }
//                                //                    println("Level 1: ${product.id} ")
//                                //                    onEvent(StoreViewModel.StoreEvent.OnProductClick(product))
//
//                            },
//                            shape = RoundedCornerShape(12.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                backgroundColor = Color.DarkGray
//                            )
//                        ) {
//                            Text(
//                                text = "See Details",
//                                color = Color.White,
//                                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
//                            )
//                        }
//                    }
//                    AsyncImage(
//                        model = meal.strMealThumb.orEmpty(),
//                        placeholder = painter,
//                        error = painter,
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .weight(1f)
//                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
//                    )
//                }
                MealCard(meal = meal)
            }
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