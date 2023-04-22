package com.example.esmt.cours.disher.feature_meals.presentation.meal_details.details_screen

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

data class MealDetailsUiState(
    val isLoading: Boolean = false,
    val favoriteButtonState: FavoriteButtonState = FavoriteButtonState(),
    val cartButtonState: CartButtonState = CartButtonState(),
    val isTopBarVisible: Boolean = false,
    val quantifiedIngredients: List<QuantifiedIngredient> = emptyList(),
    // Je ne sais pas si c'est une bonne idée ici :
    val isMealToFavorites : Boolean = false,
    val isMealIntoCart: Boolean = false,
    val detailedMeal: Meal? = null,
    val mealDetailsOption: MealDetailsOption = MealDetailsOption.INGREDIENTS,
    val error: String = "",

    ){
    companion object {
         data class QuantifiedIngredient(val name: String ,val ingredientThumb: String, val quantity: String){
             override fun toString(): String {
                 return "QuantifiedIngredient(name='$name', ingredientThumb='$ingredientThumb', quantity='$quantity')"
             }
         }
        data class FavoriteButtonState(
            val isToAdd: Boolean = false,
            val isLoading: Boolean = false,
            val text: String = "",
            val icon: ImageVector? = null
        )

        data class CartButtonState(
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


enum class MealDetailsOption {
    INGREDIENTS,
    UTENSILS
}
