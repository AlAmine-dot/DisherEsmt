package com.example.esmt.cours.disher.feature_meals.presentation.Mainsearch.main_screen

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.core.presentation.graphs.BottomBarScreen
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.presentation.search.main_screen.MainSearchUiEvent
import com.example.esmt.cours.disher.feature_meals.presentation.search.main_screen.MainSearchUiState
import com.example.esmt.cours.disher.feature_meals.presentation.search.main_screen.MainSearchViewModel
import com.example.esmt.cours.disher.ui.theme.DarkTurquoise
import com.example.esmt.cours.disher.ui.theme.LightTurquoise

import com.example.esmt.cours.disher.ui.theme.MeltyGreen
import com.example.esmt.cours.disher.ui.theme.MeltyGreenLO
import com.example.esmt.cours.disher.ui.theme.TextWhite
import com.example.esmt.cours.disher.core.presentation.main_screen.UiEvent
import com.example.esmt.cours.disher.feature_meals.presentation.cart.CartUiEvent
import com.example.esmt.cours.disher.ui.customized_items.TopAppBar2
import com.example.esmt.cours.disher.ui.customized_items.TopBarContent

@Composable
fun MainSearchScreen(
    onShowOverview : (MainSearchUiEvent.RedirectToSearchScreen) -> Unit,
    onShowCategoryDetails : (MainSearchUiEvent.RedirectToCategoryScreen) -> Unit,
    onNavigate: (MainSearchUiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    mainSearchViewModel: MainSearchViewModel = hiltViewModel(),
    sendMainUiEvent: (UiEvent) -> Unit,
){

    val mainSearchUiState by mainSearchViewModel.uiState.collectAsState()
    val categories = mainSearchViewModel.getMealCategories()
    val onMainSearchTextChange: (String) -> Unit = { query -> mainSearchViewModel.onMainSearchTextChange(query) }
    val onEvent: (MainSearchUiEvent) -> Unit = { event -> mainSearchViewModel.onEvent(event) }

    LaunchedEffect(key1 = true) {
        mainSearchViewModel.uiEvent.collect { event ->
            when (event) {
                is MainSearchUiEvent.ShowSnackbar -> {
                    sendMainUiEvent(UiEvent.HideSnackbar)
                    sendMainUiEvent(UiEvent.ShowSnackbar(event.message, event.action))
                }
                is MainSearchUiEvent.RedirectToSearchScreen -> {
                    val query = event.query
                    onShowOverview(MainSearchUiEvent.RedirectToSearchScreen(query))
                }
                is MainSearchUiEvent.RedirectToCategoryScreen -> {
                    val id = event.idCategory
                    onShowCategoryDetails(MainSearchUiEvent.RedirectToCategoryScreen(id))
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar2(
                TopBarContent(BottomBarScreen.Search.route, emptyList()),
                true,
                {},
                onNavigate = { onNavigate(MainSearchUiEvent.Navigate(it)) }
            ) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            HeaderComponent(mainSearchUiState, onMainSearchTextChange, onEvent)
            CategoryListComponent(
                categories,
                { idCategory ->
                    mainSearchViewModel.sendUiEvent(
                        MainSearchUiEvent.RedirectToCategoryScreen(
                            idCategory
                        )
                    )
                },
                false
            )
            // C'est plutot une categoryList component qu'il faudra ici :

//        MealsListComponent(
//            mealItems = mainSearchUiState.MainsearchResult,
//            onNavigate = onNavigate,
//            isMainSearching = mainSearchUiState.isMainSearching,
//            onMealClicked = {mealId ->
//                onShowMealDetailsScreen(MainSearchUiEvent.ShowMealDetails(mealId))
//            }
//        )
        }
    }
}

@Composable
fun CategoryListComponent(
    categoryItems: List<Category>,
    onCategoryClicked: (categoryId : Int) -> Unit,
    isLoading: Boolean
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                
                Text(
                    text =
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = DarkTurquoise,
                            )
                        ) {
                            append("You can search recipes by their name, for example \n",
                            )
                        }
                        withStyle(
                            style = SpanStyle(
                                color = MeltyGreen,
                            )
                        ) {
                            append("“ Sushi, beef, pizza... ”",
                            )
                        }
                    },
                    color = LightTurquoise,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
                Box(
                    contentAlignment = Alignment.Center
                ){
                    Divider(
                        color = Color.LightGray,
                        thickness = .5.dp,
                        modifier = Modifier
                            .padding(horizontal = 20.dp),
                    )
                    Text(
                        text = "OR",
                        style = MaterialTheme.typography.h6,
                        color = MeltyGreenLO,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color.White)
                            .padding(horizontal = 7.dp)
                    )
                }
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            if(isLoading){
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    CircularProgressIndicator(color = MeltyGreen)
                }
            }
            else {
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
                    items(categoryItems.size, key = { categoryItems[it].categoryId }) {
                        CategoryItem(
                            category = categoryItems[it],
                            onCategoryClicked
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    onCategoryClicked: (categoryId: Int) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCategoryClicked(category.categoryId) }
        ,
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ){
        Box(
            modifier = Modifier
                .height(170.dp)
                .fillMaxSize()
//            ,

        ){
            val painter = rememberImagePainter(
                data = category.categoryThumb,
                builder = {
                    crossfade(durationMillis = 1200)
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_placeholder)
                }
            )
            Image(
                painter = painter,
                contentDescription = "Image ${category.categoryName}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Black.copy(alpha = .35f)
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    category.categoryName,
                    style = MaterialTheme.typography.h5,
                    color = TextWhite,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

//@Composable
//fun MealsListComponent(
//    mealItems: List<Meal>,
//    onNavigate: (MainSearchUiEvent.Navigate) -> Unit,
//    isMainSearching: Boolean,
//    onMealClicked : (mealId: Int) -> Unit,
////    onDeleteClicked: (meal: Meal) -> Unit
//){
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(20.dp)
//
//        ){
//
////            Column(
////                modifier = Modifier
////                    .fillMaxWidth(),
////                horizontalAlignment = Alignment.CenterHorizontally,
////                verticalArrangement = Arrangement.Center
////            ){
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 15.dp),
//                    horizontalArrangement = Arrangement.Start,
//                    verticalAlignment = Alignment.CenterVertically
//                ){
//                    Text(
//                        text = if(isMainSearching) {
//                            "MainSearching recipes..."
//                        }else {
//                            "${if (mealItems.size == 0) "No" else mealItems.size} recipe${if (mealItems.size != 1) "s" else ""} found"
//
//                        },
//                        style = MaterialTheme.typography.h5,
//                        color = LightTurquoise,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Spacer(modifier = Modifier.width(5.dp))
//                }
//                Divider(
//                    color = Color.LightGray,
//                    thickness = .5.dp,
//                    modifier = Modifier
//                    .padding(horizontal = 20.dp),
//                    )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .heightIn(min = 20.dp)
//                        .padding(horizontal = 20.dp)
//
//                    ,
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ){
//                    Column(
//                        modifier = Modifier
//
//                            ,
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ){
//                        Row(
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically
//                        ){
//                            Icon(
//                                imageVector = Icons.Default.DateRange, contentDescription = "Filter",
//                                tint = DarkTurquoise,
//                                modifier = Modifier.size(23.dp)
//                            )
//                            Spacer(modifier = Modifier.width(5.dp))
//                            Text(
//                                "Filter",
//                                textDecoration = TextDecoration.Underline,
//                            style = MaterialTheme.typography.body1,
//                            fontWeight = FontWeight.Bold
//                            )
//                        }
//
//                    }
//
//                    Column(
//                        modifier = Modifier
//                        ,
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ){
//
//                        Row {
//                            Text(
//                                "Sort by :",
//                                style = MaterialTheme.typography.body1,
//                                color = Color.LightGray,
//
//                                )
//                            Spacer(modifier = Modifier.width(2.dp))
//                            DropdownMenuComponent()
//                        }
//                    }
//                }
////            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceAround,
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                if(isMainSearching){
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ){
//                        CircularProgressIndicator(color = MeltyGreen)
//                    }
//                }else {
////                    ajoute le link vers les détails
//
//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(2),
//                        contentPadding = PaddingValues(
//                            start = 7.5.dp,
//                            end = 7.5.dp,
//                            bottom = 100.dp
//                        ),
//                        modifier = Modifier.fillMaxHeight(),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp),
//                        verticalArrangement = Arrangement.spacedBy(10.dp)
//                    ) {
//                        items(mealItems.size, key = { mealItems[it].id }) {
//                            MealMainSearchedItem(
//                                meal = mealItems[it],
//                                onMealClicked
//                            )
//                        }
//                    }
//                }
//            }
//        }
//}

@Composable
fun HeaderComponent(
    MainsearchUiState: MainSearchUiState,
    onMainSearchTextChange: (String) -> Unit,
    onEvent: (MainSearchUiEvent) -> Unit
//onPopBackStack: () -> Unit,
//onInfoClicked: () -> Unit
){
    val MainsearchedString = MainsearchUiState.mainSearchText
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 90.dp)
            .background(Color.White)
            .padding(vertical = 27.dp, horizontal = 12.dp)

    ){
        MainSearchBarComponent(
            MainsearchedString,
            onMainSearchTextChange,
            onEvent
        )
    }
}

@Composable
fun MainSearchBarComponent(
    mainSearchedString: String,
    onMainSearchTextChange: (String) -> Unit,
    onEvent: (MainSearchUiEvent) -> Unit
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
            value = mainSearchedString,
            onValueChange = onMainSearchTextChange,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {

                    onEvent(MainSearchUiEvent.onDoneMainSearching(focusManager))
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
    CategoryListComponent(listOf(
        Category(1,"Miscellaneous","test","test"),
        Category(2,"Beef","test","test"),
        Category(3,"Breakfast","test","test"),
    ),{},false)
//    MainSearchScreen({},{}, mainSearchViewModel = viewModel())
}