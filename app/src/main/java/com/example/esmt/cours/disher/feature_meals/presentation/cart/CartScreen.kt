package com.example.esmt.cours.disher.feature_meals.presentation.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.core.presentation.graphs.BottomBarScreen
import com.example.esmt.cours.disher.core.presentation.main_screen.UiEvent
import com.example.esmt.cours.disher.feature_meals.domain.model.CartItem
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.cart.CartUiEvent
import com.example.esmt.cours.disher.feature_meals.presentation.cart.CartViewModel
import com.example.esmt.cours.disher.ui.customized_items.TopAppBar2
import com.example.esmt.cours.disher.ui.customized_items.TopBarContent
import com.example.esmt.cours.disher.ui.theme.DarkTurquoise
import com.example.esmt.cours.disher.ui.theme.LightTurquoise
import com.example.esmt.cours.disher.ui.theme.MeltyGreen
import com.example.esmt.cours.disher.ui.theme.MeltyGreenLO
import com.example.esmt.cours.disher.ui.theme.TextWhite

@Composable
fun CartScreen(
    onNavigate: (CartUiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    cartViewModel: CartViewModel = hiltViewModel(),
    sendMainUiEvent: (UiEvent) -> Unit,
    onShowMealDetailsScreen: (CartUiEvent.ShowMealDetails) -> Unit,
    ){

    val cartUiState by cartViewModel.uiState.collectAsState()
    val cartItems = cartUiState.cartItemList

    LaunchedEffect(key1 = true) {

        cartViewModel.uiEvent.collect { event ->
            when (event) {
                is CartUiEvent.ShowSnackbar -> {
                    sendMainUiEvent(UiEvent.HideSnackbar)
                    sendMainUiEvent(UiEvent.ShowSnackbar(event.message, event.action))
                }
                is CartUiEvent.Navigate -> {

                }
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar2(TopBarContent(BottomBarScreen.Cart.route, emptyList()),true,{}) }
    ) { paddingValues ->

        Log.d("testCartView", cartItems.toString())

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
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

                    CartComponent(
                        cartItems = cartItems,
                        onNavigate = onNavigate,
                        onMealClicked = { mealId ->
                            Log.d("argsmealId", "Reached level 1")
                            onShowMealDetailsScreen(CartUiEvent.ShowMealDetails(mealId))
                        },
                        onDeleteClicked = { cartItem ->
                            cartViewModel.onEvent(CartUiEvent.RemoveMealFromCart(cartItem))
                        }
                    )

                }
//            items(categoryFeatures) {feature ->
//                CategoryFeature(feature, onMealClicked = { mealId ->
//                    Log.d("argsmealId", "Reached level 1")
//                    onShowMealDetailsScreen(HomeUiEvent.ShowMealDetails(mealId))
//                })
//            }
//            item{
//                Text(homeUiState.error)
//            }
            }
            Button(
                modifier = Modifier
                    .widthIn(240.dp)
                    .heightIn(130.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp),
                onClick = { onNavigate(CartUiEvent.Navigate(BottomBarScreen.Home.route)) },
                shape = RoundedCornerShape(70.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MeltyGreen),
            ) {
                Text(
                    text = "Commander",
                    style = MaterialTheme.typography.h6,
                    color = Color.White,

                    )
            }
        }
    }
}

@Composable
fun CartComponent(
    cartItems: List<CartItem>,
    onNavigate: (CartUiEvent.Navigate) -> Unit,
    onMealClicked : (mealId: Int) -> Unit,
    onDeleteClicked: (cartItem: CartItem) -> Unit
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
                text =  "${cartItems.size} recipes",
                style = MaterialTheme.typography.h5,
                color = LightTurquoise,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                modifier = Modifier
                    .widthIn(180.dp),
                onClick = { onNavigate(CartUiEvent.Navigate(BottomBarScreen.Home.route)) },
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)

            ) {
                cartItems.forEach { cartItem ->
                    CartItemCard(cart = cartItem, onMealClicked, onDeleteClicked)
                }
            }

        }
    }

}

@Composable
fun CartItemCard(
    cart: CartItem,
    onMealClicked: (mealId: Int) -> Unit,
    onDeleteClicked: (cartItem: CartItem) -> Unit
){

    val meal = cart.cartItemMeal
        Card(
            elevation = 3.dp,
            modifier = Modifier
                .width(350.dp)
                .height(170.dp)
                .clickable {
                    onMealClicked(meal.id)
                }
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize(.9f),
            ) {
                Column(
                    modifier = Modifier
                        .weight(.5f)
                        .fillMaxHeight()
                        .padding(5.dp)
                ) {
                    Box(
                        Modifier.clip(RoundedCornerShape(12.dp))
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
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(.5f)
                        .fillMaxHeight()
                ) {
                    Box(
                        Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = meal.strMealName.orEmpty(),
                            color = DarkTurquoise,
                            style = MaterialTheme.typography.body1,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier
                                .fillMaxWidth(.7f)
                                .align(Alignment.TopStart)
                                .padding(top = 8.dp, start = 3.dp)
                        )
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(53.dp)
                                .padding(10.dp)
                                .clip(CircleShape)
                                .background(MeltyGreen)
                                .align(Alignment.TopEnd)
                                .clickable {
                                    onDeleteClicked(cart)
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete meal from favorites",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(0.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(.9f)
                                .height(50.dp)
                                .padding(bottom = 10.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    1.dp,
                                    LightTurquoise.copy(alpha = .4f),
                                    RoundedCornerShape(10.dp)
                                )
                                .align(Alignment.BottomCenter)
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .clickable {

                                    }
                                ,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "—",
                                    style = MaterialTheme.typography.h5,
                                    fontSize = 28.sp,
                                    color = DarkTurquoise,
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .border(width = 1.dp, LightTurquoise.copy(alpha = .3f))
                                ,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(.8f)
                                    ,
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Location",
                                        tint = MeltyGreen,
                                        modifier = Modifier.size(20.dp),
                                    )
                                    Text(
                                        text= cart.cartItemQuantity.toString(),
                                        style = MaterialTheme.typography.h6,
                                        fontSize = 17.sp,
                                        color = MeltyGreen,
                                        modifier = Modifier
                                            .padding(bottom = 1.dp),
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .clickable {

                                    }
                                ,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "+",
                                    style = MaterialTheme.typography.h5,
                                    fontSize = 22.sp,
                                    color = DarkTurquoise
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
private fun DefaultPreview(){
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
    val mockCartItem = CartItem(1,mockMeal,2)
//    CartItemCard(cart = mockCartItem, {},{})
    CartComponent(cartItems = listOf(mockCartItem,mockCartItem), {},{},{})
}

//@Composable
//fun MealItem(
//    meal: Meal,
//    onMealClicked: (mealId: Int) -> Unit,
//    onDeleteClicked: (meal: Meal) -> Unit
//){
//    Column(
//        modifier = Modifier
//            .height(226.dp)
//            .width(200.dp),
//
//        ){
//
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(.89f)
//                .clickable { onMealClicked(meal.id) },
//            shape = RoundedCornerShape(16.dp),
//            backgroundColor = Color.White,
//            elevation = 10.dp
//        ) {
//            val painter = rememberImagePainter(
//                data = meal.strMealThumb,
//                builder = {
//                    crossfade(durationMillis = 1200)
//                    placeholder(R.drawable.ic_placeholder)
//                    error(R.drawable.ic_placeholder)
//                }
//            )
//            Image(
//                painter = painter,
//                contentDescription = "Image ${meal.strMealName}",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize()
//            )
//            Box(modifier = Modifier
//                .fillMaxSize()
//                .background(
//                    Brush.verticalGradient(
//                        colors = listOf(
//                            Color.Transparent,
//                            Color.Black
//                        ),
//                        startY = 200f
//                    )
//                )
//            ){
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(10.dp)
//                        .align(Alignment.TopStart)
//                ) {
//                    Text(
//                        text = meal.strArea.orEmpty(),
//                        color = Color.White,
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .align(Alignment.CenterStart)
//                            .clip(RoundedCornerShape(10.dp))
//                            .background(MeltyGreen)
//                            .padding(vertical = 4.dp, horizontal = 14.dp)
//                            .alpha(0.7f),
//
//                        )
//                }
//                Box(
//                    contentAlignment = Alignment.Center,
//                    modifier = Modifier
//                        .size(53.dp)
//                        .padding(10.dp)
//                        .clip(CircleShape)
//                        .background(Color(0xFFCE4F4B))
//                        .align(Alignment.TopEnd)
//                        .padding(0.dp)
//                        .clickable {
//                            onDeleteClicked(meal)
//                        }
//                ) {
//                    Icon(
//                        imageVector = Icons.Outlined.Delete,
//                        contentDescription = "Delete meal from favorites",
//                        tint = Color.White,
//                        modifier = Modifier.size(20.dp).padding(0.dp)
//                    )
//                }
////                Spacer(Modifier.weight(9f))
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .align(Alignment.BottomStart)
//                        .fillMaxHeight(0.25f),
//                    contentAlignment = Alignment.CenterStart
//                ){
//                    Text(
//                        modifier = Modifier
//                            .padding(top = 0.dp, start = 10.dp),
//                        text= meal.strMealName.orEmpty(),
//                        color = TextWhite,
//                    )
//                }
//            }
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ){
//            Row(
//                modifier = Modifier.padding(start = 3.dp),
//            ){
//                Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, contentDescription = null)
//                Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, contentDescription = null)
//                Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, contentDescription = null)
//                Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, contentDescription = null)
//                Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, contentDescription = null)
//            }
//
////            Spacer(Modifier.weight(1f))
//
//            Text(
//                modifier = Modifier.padding(end = 10.dp),
//                text="4.6/5",
//                color = LightTurquoise,
//            )
//        }
//    }
//}
//@Composable
//fun TopCardComponent(){
//    Row(
//        modifier = Modifier
//            .fillMaxWidth(1f)
//            .padding(14.dp)
//            .clip(RoundedCornerShape(20.dp))
//            .background(MeltyGreenLO)
//            .border(2.dp, MeltyGreen, RoundedCornerShape(20.dp))
//            .padding(7.dp),
//        horizontalArrangement = Arrangement.SpaceAround,
//        verticalAlignment = Alignment.CenterVertically
//    ){
//        Column(
//            modifier = Modifier
//                .widthIn(min = 75.dp)
//                .padding(vertical = 5.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy((-4).dp)
//        ){
//            Card(
//                modifier = Modifier
//                    .size(60.dp)
//                    .clip(CircleShape)
//                    .padding(7.dp),
//                elevation = 4.dp,
//                shape = CircleShape
//            ) {
//                Box(
//                    contentAlignment = Alignment.Center,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.White)
//                ) {
//                    Row(
////                        contentAlignment = Alignment.Center,
//                        modifier = Modifier
//                            .fillMaxWidth(.8f)
//                        ,
//                        horizontalArrangement = Arrangement.spacedBy(0.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ){
//                        Icon(
//                            imageVector = Icons.Default.Person,
//                            contentDescription = "Location",
//                            tint = MeltyGreen,
//                            modifier = Modifier.size(20.dp),
//                        )
//                        Text(
//                            text="1",
//                            style = MaterialTheme.typography.h6,
//                            color = MeltyGreen,
//                            modifier = Modifier
//                                .padding(bottom = 2.dp),
////                                .align(Alignment.CenterStart)
//                        )
//                    }
//                }
//            }
//            Text(
//                text = "Dishes",
//                style = MaterialTheme.typography.body1,
//                fontSize = 16.sp
//            )
//
//        }
//
//        Column(
//            modifier = Modifier.widthIn(min = 75.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy((-4).dp)
//        ){
//            Card(
//                modifier = Modifier
//                    .size(60.dp)
//                    .clip(CircleShape)
//                    .padding(7.dp),
//                elevation = 4.dp,
//                shape = CircleShape
//            ) {
//                Box(
//                    contentAlignment = Alignment.Center,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.White)
//                ) {
//                    Icon(
//                        imageVector = Icons.Outlined.Check,
//                        contentDescription = "Location",
//                        tint = MeltyGreen,
//                        modifier = Modifier.size(20.dp),
//                    )
//                }
//            }
//            Text(
//                text = "Taste",
//                style = MaterialTheme.typography.body1,
//                fontSize = 16.sp
//            )
//
//        }
//
//        Column(
//            modifier = Modifier.widthIn(min = 75.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy((-4).dp)
//        ){
//            Card(
//                modifier = Modifier
//                    .size(60.dp)
//                    .clip(CircleShape)
//                    .padding(7.dp),
//                elevation = 4.dp,
//                shape = CircleShape
//            ) {
//                Box(
//                    contentAlignment = Alignment.Center,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.White)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Menu,
//                        contentDescription = "Location",
//                        tint = MeltyGreen,
//                        modifier = Modifier.size(20.dp),
//                    )
//                }
//            }
//            Text(
//                text = "Diet",
//                style = MaterialTheme.typography.body1,
//                fontSize = 16.sp
//            )
//
//        }
//
//        Column(
//            modifier = Modifier.widthIn(min = 75.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy((-4).dp)
//        ){
//            Card(
//                modifier = Modifier
//                    .size(60.dp)
//                    .clip(CircleShape)
//                    .padding(7.dp),
//                elevation = 4.dp,
//                shape = CircleShape
//            ) {
//                Box(
//                    contentAlignment = Alignment.Center,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.White)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Build,
//                        contentDescription = "Location",
//                        tint = MeltyGreen,
//                        modifier = Modifier.size(20.dp),
//                    )
//                }
//            }
//            Text(
//                text = "Equipment",
//                style = MaterialTheme.typography.body1,
//                fontSize = 16.sp
//            )
//
//        }
//
//
//
//    }

//
//@Composable
//@Preview(showBackground = true)
//fun DefaultPreview(){
//    FavScreen({},{}, viewModel(),{},{})
//}