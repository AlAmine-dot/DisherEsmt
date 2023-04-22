package com.example.esmt.cours.disher.feature_meals.presentation.cart

import com.example.esmt.cours.disher.feature_meals.domain.model.CartItem
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

data class CartUiState (

    val isLoading: Boolean = false,
    val cartItemList: List<CartItem> = emptyList(),
    val error: String = "",

){

    override fun toString(): String {
        return "CartUiState(isLoading=$isLoading, cartItemList=$cartItemList, error='$error')"
    }
}
