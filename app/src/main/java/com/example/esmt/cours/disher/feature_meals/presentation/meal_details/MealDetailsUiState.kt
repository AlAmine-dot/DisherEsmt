package com.example.esmt.cours.disher.feature_meals.presentation.meal_details

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

data class MealDetailsUiState(
    val isLoading: Boolean = false,
    val favoriteButtonState: FavoriteButtonState = FavoriteButtonState(),
    // Je ne sais pas si c'est une bonne idée ici :
    val isMealToFavorites : Boolean = false,
    val detailedMeal: Meal? = null,
    val error: String = "",

){
    companion object {
        data class FavoriteButtonState(
            val isToAdd: Boolean = false,
            val isLoading: Boolean = false,
            val text: String = "",
            val icon: ImageVector? = null
        )
    }

    override fun toString(): String {
        return "MealDetailsUiState(isLoading=$isLoading, favoriteButtonState=$favoriteButtonState, isMealToFavorites=$isMealToFavorites, detailedMeal=$detailedMeal, error='$error')"
    }
}

