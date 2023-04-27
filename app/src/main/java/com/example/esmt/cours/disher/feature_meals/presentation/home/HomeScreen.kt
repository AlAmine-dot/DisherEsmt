package com.example.esmt.cours.disher.feature_meals.presentation.home

import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.core.presentation.graphs.BottomBarScreen
import com.example.esmt.cours.disher.core.presentation.graphs.SearchScreen
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.CategoryFeature
import com.example.esmt.cours.disher.ui.theme.*
import com.example.esmt.cours.disher.core.presentation.main_screen.UiEvent
import com.example.esmt.cours.disher.feature_meals.domain.model.CartItem
import com.example.esmt.cours.disher.feature_meals.presentation.cart.CartUiEvent
import com.example.esmt.cours.disher.ui.customized_items.RadioToggler
import com.example.esmt.cours.disher.ui.customized_items.TopAppBar2
import com.example.esmt.cours.disher.ui.customized_items.TopBarContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
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
        val swiperContent = homeUiState.swiperContent

        val trigger by remember { mutableStateOf(homeUiState.feedModeOption == FeedMode.DISCOVERY) }
        val pagerState = rememberPagerState()
        val loadedCategories = mutableListOf<CategoryFeature>()
        val shimmerCount = 5 - categoryFeatures.size.coerceAtMost(5) // calculer le nombre de CategoryShimmer() à afficher
        val shimmers = List(shimmerCount) { CategoryShimmer() } // créer une liste de CategoryShimmer()

        loadedCategories.addAll(categoryFeatures) // ajouter les catégories chargées à la liste

        LaunchedEffect(Unit){
            if(homeUiState.feedModeOption == FeedMode.CUSTOM){
                homeViewModel.onEvent(HomeUiEvent.RefreshCart)
            }
        }

    LaunchedEffect(key1 = true) {

        homeViewModel.uiEvent.collect { event ->
            when (event) {
                is HomeUiEvent.ShowSnackbar -> {
                    sendMainUiEvent(UiEvent.HideSnackbar)
                    sendMainUiEvent(UiEvent.ShowSnackbar(event.message, event.action))
                }
                else -> {
                }
            }
        }
    }

    Scaffold(
            topBar = {TopAppBar2(TopBarContent(BottomBarScreen.Home.route, emptyList()),true,{})}
        ) {paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .background(TextWhite)
                        .fillMaxSize()
                        .padding(start = 0.dp, bottom = 39.dp),
                    verticalArrangement = Arrangement.spacedBy(30.dp)
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
                    if(homeUiState.feedModeOption == FeedMode.DISCOVERY) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 0.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                AskCardComponent(onNavigate)
                            }
                        }
                        item {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 0.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                            if (swiperContent == null){
                                SwiperShimmer()
                            }else {
                                    SwiperComponent(
                                        swiperContent,
                                        onMealClicked = { mealId ->
                                            Log.d("argsmealId", "Reached level 1")
                                            onShowMealDetailsScreen(
                                                HomeUiEvent.ShowMealDetails(
                                                    mealId
                                                )
                                            )
                                        },
                                        pagerState,
                                        onAddMealToCart = { meal ->
                                            homeViewModel.onEvent(HomeUiEvent.AddMealToCart(meal))
                                        }
                                    )
                                }
                            }
                        }
                        item{
                            for (item in loadedCategories + shimmers) {
                                Spacer(modifier =  Modifier.height(10.dp))
                                when (item) {
                                    is CategoryFeature -> {
                                        // Si c'est une CategoryFeature, afficher la CategoryFeature correspondante
                                        item.category?.let { Log.d("testcategory", it.categoryName) }
                                        CategoryFeature(
                                            item,
                                            onMealClicked = { mealId ->
                                            onShowMealDetailsScreen(HomeUiEvent.ShowMealDetails(mealId))
                                             },
                                            onAddMealToCart = { meal ->
                                                homeViewModel.onEvent(HomeUiEvent.AddMealToCart(meal))
                                            }
                                        )
                                    }
                                    else -> {
                                        // Si c'est un CategoryShimmer, afficher le CategoryShimmer correspondant
                                        CategoryShimmer()
                                    }
                                }
                                Spacer(modifier =  Modifier.height(10.dp))

                            }
                        }

//                        items(count = 5) { index ->
//                            val feature = categoryFeatures.getOrNull(index) // récupérer la catégorie à l'index donné
//                            if (feature != null) {
//                                // Si la catégorie est chargée, afficher la CategoryFeature correspondante
//                                feature.category?.let { Log.d("testcategory", it.categoryName) }
//                                CategoryFeature(feature, onMealClicked = { mealId ->
//                                    Log.d("argsmealId", "Reached level 1")
//                                    onShowMealDetailsScreen(HomeUiEvent.ShowMealDetails(mealId))
//                                })
//                                loadedCount++
//                            } else if (loadedCount < index) {
//                                // Si la catégorie n'est pas encore chargée mais qu'il y a des catégories chargées avant, afficher une CategoryShimmer()
//                                CategoryShimmer()
//                            }
//                        }


                        item {
                            Text(homeUiState.error)
                        }
                    }else {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 0.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CartCardComponent(homeUiState.userCart,onNavigate)
                            }
                        }

                    }
                }
        }

}

@Composable
fun CartCardComponent(
    cartItems: List<CartItem>,
    onNavigate: (HomeUiEvent.Navigate) -> Unit
){
    val cartItemQuantity = remember { mutableStateOf(1) }

    Card(
        modifier = Modifier
            .fillMaxWidth(.90f)
            .width(300.dp)
//            .height(350.dp)
        ,
        elevation = 13.dp,
        shape = RoundedCornerShape(18.dp),
        backgroundColor = MeltyGreenVLO
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 15.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = DarkTurquoise,
                            )
                        ) {
                            append(
                                "Hello, ",
                            )
                        }
                        withStyle(
                            style = SpanStyle(
                                color = MeltyGreen,
                            )
                        ) {
                            append(
                                "fellow disher !",
                            )
                        }
                    },
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
            }
            if(!cartItems.isEmpty()) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = DarkTurquoise,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                )
                            ) {
                                append(
                                    "${cartItems.size} recipes \n",
                                )
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = DarkTurquoise,
                                    fontSize = 18.sp

                                )
                            ) {
                                append(
                                    "in your cart",
                                )
                            }
                        },
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(70.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy((-35).dp)
                    ) {
                        cartItems.take(3).onEach { cartItem ->
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(CircleShape)
                                    .border(3.dp, MeltyGreen, CircleShape),
                            ) {
                                val painter = rememberImagePainter(
                                    data = cartItem.cartItemMeal.strMealThumb,
                                    builder = {
                                        crossfade(durationMillis = 1200)
                                        placeholder(R.drawable.ic_placeholder)
                                        error(R.drawable.ic_placeholder)
                                    }
                                )
                                Image(
                                    painter = painter,
                                    contentDescription = "Image ${cartItem.cartItemMeal.strMealName}",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                if (cartItems.size > 3 && cartItems.indexOf(cartItem) == 2) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.Black.copy(alpha = .4f))
                                            .padding(3.dp)
                                    ) {
                                        Text(
                                            text = "+${cartItems.size - 3}",
                                            color = Color.White,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                }


                            }
                        }

                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(13.dp)

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .heightIn(50.dp),
                            onClick = { onNavigate(HomeUiEvent.Navigate(BottomBarScreen.Cart.route)) },
                            shape = RoundedCornerShape(70.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MeltyGreen),
                        ) {
                            Text(
                                text = "See cart",
                                style = MaterialTheme.typography.h6,
                                color = Color.White,
                                fontSize = 17.sp
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { },
                            border = BorderStroke(2.dp, MeltyGreen),
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(47.dp),
                            shape = RoundedCornerShape(70.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Discard",
                                    fontSize = 17.sp,
                                    color = DarkTurquoise,
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp, vertical = 0.dp)
                                )
                            }
                        }

                    }
                }
            }else{

                Row(
                ){
                    Text("Your cart is empty for now, how many meals would you like to plan ?")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Row(
                        modifier = Modifier
                            .width(110.dp)
                            .height(35.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                1.dp,
                                LightTurquoise.copy(alpha = .4f),
                                RoundedCornerShape(10.dp)
                            )
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .clickable {
                                    if(cartItemQuantity.value > 1){
                                        cartItemQuantity.value = cartItemQuantity.value - 1
                                    }
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
                                Text(
                                    text= cartItemQuantity.value.toString(),
//                                    text = "1",
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
//                                    onUpdateQuantity(cart, true)
                                    if(cartItemQuantity.value < 7){
                                        cartItemQuantity.value = 1 + cartItemQuantity.value
                                    }
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
                    Button(
                        modifier = Modifier
                            .widthIn(80.dp)
                            .heightIn(40.dp),
                        onClick = {  },
                        shape = RoundedCornerShape(70.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MeltyGreen),
                    ) {
                        Text(
                            text = "Generate menu",
                            style = MaterialTheme.typography.h6,
                            fontSize = 17.sp,
                            color = Color.White,
                            )
                    }
                }
            }
        }


    }
}

@Composable
fun SwiperShimmer(){
    Column(
    ) {
        Box(
            modifier = Modifier
                .height(290.dp)
                .width(360.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(16.dp))
                .shimmerEffect()
            ,

        ){
            
        }
        Row(
            modifier = Modifier
                .width(360.dp)
                .padding(horizontal = 15.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween

        ){
            Column(
                modifier = Modifier
                    .width(130.dp)
                    .height(26.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            ){

            }
            Column(
                modifier = Modifier
                    .width(25.dp)
                    .height(26.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            ){

            }
        }
        Row(
            modifier = Modifier
                .width(360.dp)
                .height(20.dp)
                .padding(horizontal = 15.dp)
            ,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .height(10.dp)
                    .width(10.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            ){

            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .height(10.dp)
                    .width(10.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            ){

            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .height(10.dp)
                    .width(10.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            ){

            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .height(10.dp)
                    .width(10.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            ){

            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .height(10.dp)
                    .width(10.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            ){

            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .height(10.dp)
                    .width(10.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            ){

            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SwiperComponent(
    feature: CategoryFeature,
    onMealClicked: (id: Int) -> Unit,
    pagerState: PagerState,
    onAddMealToCart: (meal: Meal) -> Unit

){
    Column(
        modifier = Modifier.background(TextWhite)
    ) {
        HorizontalPager(
            modifier = Modifier.background(TextWhite),
            count = feature.featuredMeals.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            SwiperMealCard(meal = feature.featuredMeals[position], onClick = { id ->
            Log.d("argsmealId", "Reached level 2")
            onMealClicked(id)},
                onAddMealToCart = onAddMealToCart
            )
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            activeColor = MeltyGreen,
            pagerState = pagerState
        )
    }
}

@Composable
fun SwiperMealCard(
    meal: Meal,
    onClick: (id: Int) -> Unit,
    onAddMealToCart: (meal: Meal) -> Unit
){
    Column(
        modifier = Modifier
            .height(290.dp)
            .width(350.dp)
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
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .align(Alignment.CenterEnd)
                            .clip(CircleShape)
                            .background(MeltyGreen)
                            .shadow(100.dp, CircleShape)
                            .clickable {
                                onAddMealToCart(meal)
                            }
                        ,
                    ){
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Add meal to cart",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(16.dp),
                        )
                    }

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
fun CategoryFeature(
    feature: CategoryFeature,
    onMealClicked: (id: Int) -> Unit,
    onAddMealToCart: (meal: Meal) -> Unit
) {

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
            MealCard(
                meal = item,
                onClick = { id ->
                Log.d("argsmealId", "Reached level 2")
                onMealClicked(id)},
                onAddMealToCart = onAddMealToCart
            )
        }
        item {
            Spacer(modifier = Modifier.width(0.dp))
        }
    }

}

@Composable
fun CategoryShimmer(){

    Column(
    ){
        Row(
            modifier = Modifier
                .height(30.dp)
                .width(250.dp)
                .padding(start = 15.dp, bottom = 7.dp)
                .clip(RoundedCornerShape(5.dp))
                .shimmerEffect()
        ){

        }

        Row(
            modifier = Modifier
                .height(230.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ){
            Spacer(modifier = Modifier.width(0.dp))

            Column(){

                Column(modifier = Modifier
                    .fillMaxHeight(.9f)
                    .width(200.dp)
                    .padding(vertical = 7.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shimmerEffect()) {
                }
                Row(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(horizontal = 3.dp)
                        .height(17.dp)
//                        .shimmerEffect()
                ){
                    Row(
                        modifier = Modifier
                            .width(210.dp)
                            .height(20.dp)
                            .padding(start = 3.dp, end = 3.dp)
                        ,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Column(
                            modifier = Modifier
                                .width(100.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(5.dp))

                                .shimmerEffect()
                        ){

                        }
                        Column(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .shimmerEffect()
                        ){

                        }
//                        StarBox(modifier = Modifier.shimmerEffect())
//                        Icon(imageVector = Icons.Outlined.Star, contentDescription = null, modifier = Modifier.shimmerEffect())
                    }
                }
//                Row(
//                    modifier = Modifier
//                        .width(20.dp)
//                        .padding(vertical = 15.dp)
//                        .shimmerEffect()
//                    ,
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ){
//                    Row(
//                        modifier = Modifier
//                            .padding(start = 3.dp)
//                            .shimmerEffect()
//                        ,
//                    ){
////                        Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, contentDescription = null)
//                    }
//
//
//                    Row(
//                        modifier = Modifier
//                            .height(20.dp)
//                            .width(23.dp)
//                            .padding(start = 15.dp,bottom = 7.dp)
//                            .shimmerEffect()
//                    ){
//
//                    }
//                }
            }

            Column(){

                Column(modifier = Modifier
                    .fillMaxHeight(.9f)
                    .width(200.dp)
                    .padding(vertical = 7.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                    .shimmerEffect()) {
                }
                Row(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(horizontal = 3.dp)
                        .height(20.dp)
//                        .shimmerEffect()
                ){
                    Row(
                        modifier = Modifier
                            .width(200.dp)
                            .height(16.dp)
                            .padding(start = 3.dp, end = 3.dp)
                        ,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Column(
                            modifier = Modifier
                                .width(120.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .shimmerEffect()
                        ){

                        }
                    }
                }
            }
        }
    }

}

@Composable
fun MealCard(
    meal: Meal,
    onClick: (id: Int) -> Unit,
    onAddMealToCart: (meal: Meal) -> Unit
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
                        Box(
                            modifier = Modifier
                                .size(26.dp)
                                .align(Alignment.CenterEnd)
                                .clip(CircleShape)
                                .background(MeltyGreen)
                                .shadow(100.dp, CircleShape)
                                .clickable {
                                    onAddMealToCart(meal)
                                }
                            ,
                        ){
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Add meal to cart",
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(16.dp),
                            )
                        }

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
    val mockCartItem1 = CartItem(1 ,mockMeal,2)
    val mockCartItem2 = CartItem(2 ,mockMeal,1)
    val mockCartItem3 = CartItem(3 ,mockMeal,12)
    val mockCartItem4 = CartItem(4 ,mockMeal,6)

    MealCard(mockMeal,{},{})
//    CategoryShimmer()
//    SwiperShimmer()
//    CartCardComponent(
////            listOf(mockCartItem1,mockCartItem2,mockCartItem3,mockCartItem4),
//        emptyList(),
//        {}
//    )
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5).copy(alpha = .4f),
                Color(0xFF8F8B8B).copy(alpha = .4f),
                Color(0xFFB8B5B5).copy(alpha = .4f),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}