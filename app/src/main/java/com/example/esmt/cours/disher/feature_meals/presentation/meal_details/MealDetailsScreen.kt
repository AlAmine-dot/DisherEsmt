package com.example.esmt.cours.disher.feature_meals.presentation.meal_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeUiEvent
import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeViewModel

@Composable
fun MealDetailsScreen(
    onNavigate: (HomeUiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    mealId: Int,
    mealDetailsViewModel: MealDetailsViewModel = hiltViewModel()
){


    val mealDetailsUiState by mealDetailsViewModel.uiState.collectAsState()

    // Ceci est une side effect car cet appel modifie une partie de ton code qui n'a rien à voir avec
    // le composable, pour éviter que cet effet de bord ne créee une boucle, utilise LaunchedEffect :

    LaunchedEffect(mealId) {
        // Je ne sais pas si c'est censé arriver, mais quelque chose me dit que c'est
        // peut-être parce que la vue s'occupe de quelque chose qu'elle n'est pas censée faire
        // optimiser la façon dont tu récupères la recette est envisageable
        mealDetailsViewModel.getDetailedMeal(mealId)
    }

    val detailedMeal = mealDetailsUiState.detailedMeal

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.clickable {},
            text = "${detailedMeal?.strMealName}",
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}