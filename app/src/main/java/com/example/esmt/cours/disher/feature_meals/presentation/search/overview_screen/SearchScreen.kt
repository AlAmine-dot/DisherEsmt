package com.example.esmt.cours.disher.feature_meals.presentation.search.overview_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.ui.theme.DarkTurquoise
import com.example.esmt.cours.disher.ui.theme.LightBrown
import com.example.esmt.cours.disher.ui.theme.LightTurquoise
import com.example.esmt.cours.disher.ui.theme.MeltyGreen
import com.example.esmt.cours.disher.ui.theme.MeltyGreenLO
import com.example.esmt.cours.disher.ui.theme.TextWhite
import com.example.esmt.cours.disher.core.presentation.main_screen.UiEvent

@Composable
fun SearchScreen(
    onNavigate: (SearchUiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
    sendMainUiEvent: (UiEvent) -> Unit,
    onShowMealDetailsScreen: (SearchUiEvent.ShowMealDetails) -> Unit,
    query: String
){

    val searchUiState by searchViewModel.uiState.collectAsState()
    val onSearchTextChange: (String) -> Unit = { query -> searchViewModel.onSearchTextChange(query) }
    val onEvent: (SearchUiEvent) -> Unit = { event -> searchViewModel.onEvent(event) }
    val resultList = remember {searchUiState.searchResult}

    LaunchedEffect(key1 = true, query){
        onSearchTextChange(query)
        searchViewModel.onEvent(SearchUiEvent.onDoneSearching(null))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeaderComponent(searchUiState,onSearchTextChange,onEvent,onPopBackStack)
        MealsListComponent(
            mealItems = searchUiState.searchResult,
            onNavigate = onNavigate,
            isSearching = searchUiState.isSearching,
            onMealClicked = {mealId ->
                onShowMealDetailsScreen(SearchUiEvent.ShowMealDetails(mealId))
            }
        )
    }
}

@Composable
private fun MealsListComponent(
    mealItems: List<Meal>,
    onNavigate: (SearchUiEvent.Navigate) -> Unit,
    isSearching: Boolean,
    onMealClicked : (mealId: Int) -> Unit,
//    onDeleteClicked: (meal: Meal) -> Unit
){

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)

        ){

//            Column(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = if(isSearching) {
                            "Searching recipes..."
                        }else {
                            "${if (mealItems.size == 0) "No" else mealItems.size} recipe${if (mealItems.size != 1) "s" else ""} found"

                        },
                        style = MaterialTheme.typography.h5,
                        color = LightTurquoise,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
                Divider(
                    color = Color.LightGray,
                    thickness = .5.dp,
                    modifier = Modifier
                    .padding(horizontal = 20.dp),
                    )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 20.dp)
                        .padding(horizontal = 20.dp)

                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(
                        modifier = Modifier

                            ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Icon(
                                imageVector = Icons.Default.DateRange, contentDescription = "Filter",
                                tint = DarkTurquoise,
                                modifier = Modifier.size(23.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                "Filter",
                                textDecoration = TextDecoration.Underline,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold
                            )
                        }

                    }

                    Column(
                        modifier = Modifier
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){

                        Row {
                            Text(
                                "Sort by :",
                                style = MaterialTheme.typography.body1,
                                color = Color.LightGray,

                                )
                            Spacer(modifier = Modifier.width(2.dp))
                            DropdownMenuComponent()
                        }
                    }
                }
//            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ){
                if(isSearching){
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        CircularProgressIndicator(color = MeltyGreen)
                    }
                }else {
//                    ajoute le link vers les détails

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            start = 7.5.dp,
                            end = 7.5.dp,
                            bottom = 100.dp
                        ),
                        modifier = Modifier.fillMaxHeight(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(mealItems.size, key = { mealItems[it].id }) {
                            MealSearchedItem(
                                meal = mealItems[it],
                                onMealClicked
                            )
                        }
                    }
                }
            }
        }
}

@Composable
private fun DropdownMenuComponent() {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Relevance", "Rating", "Reviews")
    var selectedOption by remember { mutableStateOf(options[1]) }

    Column {
        // Bouton pour afficher le menu
        Row(
            modifier = Modifier
                .widthIn(76.dp)
        ){
            Text(
                selectedOption,
                modifier = Modifier.widthIn(76.dp)
            )
            Icon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = "Dropdown menu",
                modifier = Modifier.clickable {
                    expanded = true
                }
            )
        }

        // Menu déroulant
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // Liste d'options à afficher
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectedOption = option
                    }
                ) {
                    Text(option)
                }
            }
        }
    }
}


@Composable
private fun MealSearchedItem(
    meal: Meal,
    onMealClicked: (mealId: Int) -> Unit,
//    onDeleteClicked: (meal: Meal) -> Unit
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
                .clickable {
                    onMealClicked(meal.id)
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
fun HeaderComponent(
    searchUiState: SearchUiState,
    onSearchTextChange: (String) -> Unit,
    onEvent: (SearchUiEvent) -> Unit,
    onPopBackStack: () -> Unit,
//onInfoClicked: () -> Unit
){
    val searchedString = searchUiState.searchText
    val searchHeadingTitle = searchUiState.searchHeadingTitle
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 90.dp)
            .background(Color.White)
            .padding(vertical = 27.dp, horizontal = 12.dp)

    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = DarkTurquoise,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        onPopBackStack()
                    }
            )
            Text(
                text = searchHeadingTitle,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth(.7f)
            )
        }
        SearchBarComponent(
            searchedString,
            onSearchTextChange,
            onEvent
        )
    }
}

@Composable
fun SearchBarComponent(
    searchedString: String,
    onSearchTextChange: (String) -> Unit,
    onEvent: (SearchUiEvent) -> Unit
){
    val focusManager = LocalFocusManager.current

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(26.dp))
            .background(Color.White)
            .border(2.dp, MeltyGreenLO, RoundedCornerShape(26.dp))
    ){

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = MeltyGreen,
            modifier = Modifier
                .padding(start = 12.dp, end = 6.dp)
                .size(30.dp)
        )
        TextField(
            value = searchedString,
            onValueChange = onSearchTextChange,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {

                    onEvent(SearchUiEvent.onDoneSearching(focusManager))
//                focusManager.clearFocus()
            }),
            placeholder = {
                Text(
                    text = "Search recipes, ingredients...",
                    color = Color.LightGray
                )
            },
            textStyle = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.DarkGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )

    }
}
@Composable
@Preview(showBackground = true)
fun DefaultPreviewer(){
//    SearchScreen({},{}, searchViewModel = viewModel(),{},{})
}