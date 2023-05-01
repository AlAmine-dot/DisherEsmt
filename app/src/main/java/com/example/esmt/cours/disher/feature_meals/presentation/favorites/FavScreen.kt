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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.core.presentation.graphs.BottomBarScreen
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.favorites.FavUiEvent
import com.example.esmt.cours.disher.feature_meals.presentation.favorites.FavViewModel
import com.example.esmt.cours.disher.ui.theme.*
import com.example.esmt.cours.disher.core.presentation.main_screen.UiEvent
import com.example.esmt.cours.disher.feature_meals.presentation.cart.CartUiEvent
import com.example.esmt.cours.disher.ui.customized_items.TopAppBar2
import com.example.esmt.cours.disher.ui.customized_items.TopBarContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
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

//    Scaffold(
//        modifier = Modifier.zIndex(1f),
//        topBar = { TopAppBar2(TopBarContent(BottomBarScreen.Favorites.route, emptyList()),true,{}) }
//    ) { paddingValues ->
//
//        val sheetState = rememberBottomSheetState(
//            initialValue = BottomSheetValue.Collapsed
//        )
//        val scaffoldSheetState = rememberBottomSheetScaffoldState(
//            bottomSheetState = sheetState
//        )
//        val scope = rememberCoroutineScope()
//
//        BottomSheetScaffold(
//            modifier = Modifier.zIndex(1f),
//            scaffoldState = scaffoldSheetState,
//            sheetContent = {
//            Box(
//                modifier = Modifier
//                    .zIndex(1f)
//                    .fillMaxWidth()
//                    .height(300.dp)
//            ){
//                Text("Hello sheet !", style = MaterialTheme.typography.h5)
//            }
//        },
//            backgroundColor = Color.White,
//            sheetElevation = 16.dp,
//            sheetPeekHeight = 0.dp,
//            sheetShape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)
//        ) {
//
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//                .padding(top = 7.dp),
//        ) {
//            TopCardComponent()
//            MealsListComponent(
//                mealItems = favoriteMeals,
//                onNavigate = onNavigate,
//                onMealClicked = { mealId ->
//                    Log.d("argsmealId", "Reached level 1")
//                    onShowMealDetailsScreen(FavUiEvent.ShowMealDetails(mealId))
//                },
//                onDeleteClicked = { meal ->
//                    favViewModel.onEvent(FavUiEvent.RemoveMealFromFavorites(meal))
//                },
//                onToggleBottomSheet = {
//                    scope.launch {
//                        if (sheetState.isCollapsed) {
//                            sheetState.expand()
//                        } else {
//                            sheetState.collapse()
//                        }
//                    }
//                }
//            )
//        }
//        }
//
//    }
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar2(
            TopBarContent(BottomBarScreen.Favorites.route, emptyList()),
            true,
            {},
            onNavigate = { onNavigate(FavUiEvent.Navigate(it)) }
            )
        }
    ) { paddingValues ->


            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(top = 7.dp),
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
                    },
                    onToggleBottomSheet = {
                        scope.launch {
                            if (sheetState.isVisible) {
                                sheetState.hide()
                            } else {
                                sheetState.show()
                            }
                        }
                    }
                )
            }
        }
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ){
                    Text("Hello sheet !", style = MaterialTheme.typography.h5)
                }
            },
            sheetShape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
            sheetElevation = 16.dp,
            sheetBackgroundColor = Color.White,
            scrimColor = Color.Black.copy(alpha = 0.4f),
            sheetContentColor = MaterialTheme.colors.onSurface,
    //            sheetContentAlpha = 1f,
    //            sheetContentAnimations = BottomSheetDefaults.AnimationSpec
        ){}
}

@Composable
fun MealsListComponent(
    mealItems: List<Meal>,
    onNavigate: (FavUiEvent.Navigate) -> Unit,
    onMealClicked : (mealId: Int) -> Unit,
    onDeleteClicked: (meal: Meal) -> Unit,
    onToggleBottomSheet: () -> Unit
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
                text =  "${mealItems.size} ${if (mealItems.size == 1) "recipe" else "recipes"}",
                style = MaterialTheme.typography.h5,
                color = LightTurquoise,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                modifier = Modifier
                    .widthIn(180.dp),
                onClick = {
//                    onNavigate(FavUiEvent.Navigate(BottomBarScreen.Home.route))
                          onToggleBottomSheet()
                          },
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
            if(mealItems.size > 0){

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
            }else{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(400.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Image(
                        painterResource(id = R.drawable.ph_favempty),
                        contentDescription = "Empty placeholder"
                    )
                    Text(
                        "You don't have any favorite recipes yet.",
                        style = MaterialTheme.typography.body1,
                        fontSize = 16.sp,
                        color = DarkTurquoise
                    )
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
                        modifier = Modifier
                            .size(20.dp)
                            .padding(0.dp)
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
                            contentDescription = "Person icon",
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
                        contentDescription = "Taste check icon",
                        tint = MeltyGreen,
                        modifier = Modifier.size(26.dp),
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
                        painterResource(id = R.drawable.ic_diet),
                        contentDescription = "Weight monitor icon",
                        tint = MeltyGreen,
                        modifier = Modifier.size(26.dp),
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
                        painterResource(id = R.drawable.ic_ustensils),
                        contentDescription = "Ustensils icon",
                        tint = MeltyGreen,
                        modifier = Modifier.size(26.dp),
                    )
                }
            }
            Text(
                text = "Ustensils",
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp
            )

        }



    }
}

@Composable
@Preview(showBackground = true)
private fun DefaultPreview(){
    FavScreen({},{}, viewModel(),{},{})
}