package com.example.esmt.cours.disher.feature_meals.presentation.meal_details

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeUiEvent
import com.example.esmt.cours.disher.ui.customized_items.GradientButton
import com.example.esmt.cours.disher.ui.theme.*
import com.example.esmt.cours.disher.core.presentation.main_screen.UiEvent
import com.example.esmt.cours.disher.ui.customized_items.RadioToggler

@Composable
fun MealDetailsScreen(
    onNavigate: (HomeUiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    mealId: Int,
    mealDetailsViewModel: MealDetailsViewModel = hiltViewModel(),
    sendMainUiEvent: (UiEvent) -> Unit,
) {


    val mealDetailsUiState by mealDetailsViewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Ceci est une side effect car cet appel modifie une partie de ton code qui n'a rien à voir avec
    // le composable, pour éviter que cet effet de bord ne créee une boucle, utilise LaunchedEffect :

    LaunchedEffect(mealId) {
        // Je ne sais pas si c'est censé arriver, mais quelque chose me dit que c'est
        // peut-être parce que la vue s'occupe de quelque chose qu'elle n'est pas censée faire
        // optimiser la façon dont tu récupères la recette est envisageable
        mealDetailsViewModel.getDetailedMeal(mealId)

        mealDetailsViewModel.uiEvent.collect { event ->
            when (event) {
                is MealDetailsUiEvent.ShowSnackbar -> {
                    sendMainUiEvent(UiEvent.HideSnackbar)
                    sendMainUiEvent(UiEvent.ShowSnackbar(event.message, event.action))
                }
                is MealDetailsUiEvent.Navigate -> {

                }
                else -> Unit
            }
        }
    }



    val detailedMeal = mealDetailsUiState.detailedMeal
    if(detailedMeal != null){
        MealDetailsApp(
            detailedMeal = detailedMeal,
            onPopBackStack,
            { event ->
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(event.uri)
            )
            startActivity(context, browserIntent, null)
            },
            {   Log.d("argsvm", "toggled last level !")
                mealDetailsViewModel.onEvent(MealDetailsUiEvent.ToggleMealFromFavorite(detailedMeal))
            },
            mealDetailsUiState,
            { mealDetailsOption ->
                mealDetailsViewModel.onEvent(MealDetailsUiEvent.ToggleMealDetailsOption(mealDetailsOption))
            }
        )
    }else{
        Text("Oops, couldn't find recipe")
    }
}

@Composable
fun MealDetailsApp(
    detailedMeal: Meal,
    onPopBackStack: () -> Unit,
    onRedirect: (MealDetailsUiEvent.RedirectToURI) -> Unit,
    onToggleFavorite: () -> Unit,
    uiState: MealDetailsUiState,
    onToggleMealDetailsOption: (MealDetailsOption) -> Unit
){

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(bottom = 0.dp)

    ) {
        item {
            HeaderComponent(detailedMeal.strMealName.orEmpty(), onPopBackStack = onPopBackStack, onInfoClicked = {onRedirect(MealDetailsUiEvent.RedirectToURI(detailedMeal.strSource))})
            HeroComponent(detailedMeal.strMealThumb.orEmpty())
            AboutComponent(mealCategory = detailedMeal.strCategory.orEmpty(), mealArea = detailedMeal.strArea.orEmpty(), onClickFavoriteButton = onToggleFavorite, favoriteButtonState = uiState.favoriteButtonState)
            DetailsComponent(uiState.mealDetailsOption,onToggleMealDetailsOption,uiState.quantifiedIngredients)
        }
    }
}

@Composable
fun DetailsComponent(
    mealDetailsOption: MealDetailsOption,
    onToggleMealDetailsOption: (MealDetailsOption) -> Unit,
    quantifiedIngredients: List<MealDetailsUiState.Companion.QuantifiedIngredient>
){
//    var trigger by remember { mutableStateOf(mealDetailsOption == MealDetailsOption.INGREDIENTS) }
    Spacer(modifier = Modifier.height(15.dp))
    Column(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize()
        ,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(25f, 0f),
                        end = Offset((size.width - 25f), 0f),
                        strokeWidth = (0.4.dp).toPx()
                    )
                }
                .padding(vertical = 15.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Recipe's duration",
                    tint = DarkTurquoise,
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "55 min",
                    color = DarkTurquoise,
                    style = MaterialTheme.typography.body1,
                )
            }
            Text(
                "•",
                color = DarkTurquoise,
                style = MaterialTheme.typography.body1,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = "Cost",
                    tint = DarkTurquoise,
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Expensive",
                    color = DarkTurquoise,
                    style = MaterialTheme.typography.body1,
                )
            }
            Text(
                "•",
                color = DarkTurquoise,
                style = MaterialTheme.typography.body1,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Recipe's duration",
                    tint = DarkTurquoise,
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "55 min",
                    color = DarkTurquoise,
                    style = MaterialTheme.typography.body1,
                )
            }


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            RadioToggler(
                item1 = "Ingredients",
                item2 = "Ustensils",
                mealDetailsOption == MealDetailsOption.INGREDIENTS,
                {onToggleMealDetailsOption(MealDetailsOption.INGREDIENTS)},
                {onToggleMealDetailsOption(MealDetailsOption.UTENSILS)})
        }
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){

            if(mealDetailsOption == MealDetailsOption.INGREDIENTS){
                Log.d("argstestvms", quantifiedIngredients.toString())
//                IngredientsDetailsSubComponent(quantifiedIngredients)
                IngredientGrid(items = quantifiedIngredients)
            }else{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(500.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Image(
                        painterResource(id = R.drawable.ph_emptysection),
                        contentDescription = "Empty placeholder"
                    )
                    Text(
                        "Oops, seems like we still gotta work on this section :/",
                        style = MaterialTheme.typography.body1,
                        color = DarkTurquoise
                    )
                }
            }

        }
    }

}


@Composable
fun IngredientGrid(items: List<MealDetailsUiState.Companion.QuantifiedIngredient>) {
    val chunkedItems = items.chunked(3)

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        for (row in chunkedItems) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                for (item in row) {
                    Column(
                        modifier = Modifier
                            .widthIn(max = 120.dp),
//                            .heightIn(max = 100.dp),
                    ){

                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(15.dp),
                            elevation = 5.dp
                        ) {
                            // Contenu de la carte ici
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                val painter = rememberImagePainter(
                                    data = item.ingredientThumb,
                                    builder = {
                                        crossfade(durationMillis = 1200)
                                        placeholder(R.drawable.ic_placeholder)
                                        error(R.drawable.ic_placeholder)
                                    }
                                )
                                Image(
                                    painter = painter,
                                    contentDescription = "Image ${item.name}",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            
                            Text(
                                text = "${item.quantity}",
                                style = MaterialTheme.typography.body1,
                                color = DarkTurquoise,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = "${item.name}",
                                style = MaterialTheme.typography.body1,
                                color = DarkTurquoise,
                                textDecoration = TextDecoration.Underline,
                                textAlign = TextAlign.Center

                            )
                        }


                    }

                }
            }
        }
    }
}


@Composable
fun AboutComponent(
    mealCategory: String,
    mealArea: String,
    favoriteButtonState: MealDetailsUiState.Companion.FavoriteButtonState?,
    onClickFavoriteButton: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .heightIn(min = 110.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.widthIn(min = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy((-4).dp)
            ){
                    Card(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .padding(10.dp),
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
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = MeltyGreen,
                                modifier = Modifier.size(22.dp),
                            )
                        }
                    }
                    Text(
                        text = mealArea,
                        style = MaterialTheme.typography.body1,
                        fontSize = 16.sp
                    )
            }

            Column(
                modifier = Modifier.widthIn(min = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy((-4).dp)
            ){
                Card(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .padding(10.dp),
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
                            imageVector = Icons.Default.List,
                            contentDescription = "List",
                            tint = MeltyGreen,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                }
                Text(
                    text= mealCategory,
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp
                )
            }

            Column(
                modifier = Modifier.widthIn(min = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy((-4).dp)
            ){
                Card(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .padding(10.dp),
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
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Thumb up",
                            tint = MeltyGreen,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                }
                Text(
                    text= "Easy",
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp
                )
            }

        }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            GradientButton(
                text = favoriteButtonState?.text.orEmpty(),
                textColor = Color.White,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        MeltyGreen,
                        MeltyGreen,
                        Color(0xFF11998E),
                    )
                ),
                335.dp,
                65.dp,
                20,
                onClick = onClickFavoriteButton,
                icon = favoriteButtonState?.icon
            )

            OutlinedButton(
                onClick = { /*TODO*/ },
                border = BorderStroke(3.dp, MeltyGreen),

            ) {
                    Text(
                        text = "Add to cart",
                        fontSize = 20.sp,
                        color = DarkTurquoise,
                                modifier = Modifier
                                .padding(horizontal = 98.dp, vertical = 10.dp)
                        )
            }
        }
    }

    }
}

@Composable
fun HeroComponent(
    detailedMealThumb: String
) {

        val painter = rememberImagePainter(
            data = detailedMealThumb,
//            painterResource(id = R.drawable.ic_placeholder),
            builder = {
                crossfade(durationMillis = 1200)
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_placeholder)
            }
        )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp))
            .heightIn(min = 405.dp),
    ){
            Image(
                painter = painter,
                contentDescription = "Meal image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(40.dp)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                    .background(Color.White)
                ,
            ){

            }
    }
}

@Composable
fun HeaderComponent(
    detailedMealName: String,
    onPopBackStack: () -> Unit,
    onInfoClicked: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 90.dp)
            .background(Color.White)
            .padding(vertical = 12.dp)

    ){
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
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
                    .clickable { onPopBackStack() }
            )
            Text(
                text = detailedMealName,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(.7f)
            )
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info",
                tint = MeltyGreen,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onInfoClicked() }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row(
                    modifier = Modifier.padding(start = 3.dp),
                ){
                    Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, modifier = Modifier.size(24.dp), contentDescription = null)
                    Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, modifier = Modifier.size(24.dp), contentDescription = null)
                    Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, modifier = Modifier.size(24.dp), contentDescription = null)
                    Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, modifier = Modifier.size(24.dp), contentDescription = null)
                    Icon(imageVector = Icons.Outlined.Star, tint = LightBrown, modifier = Modifier.size(24.dp), contentDescription = null)
                }

                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text="4.6/5",
                    color = LightTurquoise,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun defaultPreview(){
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
        isFavorite = false
    )

    DetailsComponent(MealDetailsOption.UTENSILS,{}, listOf(
        MealDetailsUiState.Companion.QuantifiedIngredient(
            "Carrots",
            "https://image.com/carrots.jpg",
            "2"
        ),
        MealDetailsUiState.Companion.QuantifiedIngredient(
            "Potatoes",
            "https://image.com/potatoes.jpg",
            "3"
        ),
        MealDetailsUiState.Companion.QuantifiedIngredient(
            "Onions",
            "https://image.com/onions.jpg",
            "1"
        ),
        MealDetailsUiState.Companion.QuantifiedIngredient(
            "Tomatoes",
            "https://image.com/tomatoes.jpg",
            "4"
        ),
        MealDetailsUiState.Companion.QuantifiedIngredient(
            "Peppers",
            "https://image.com/peppers.jpg",
            "2"
        )
    ))
//    MealDetailsApp(detailedMeal = mockMeal, onPopBackStack = {},{},MealDetailsUiState())
}
