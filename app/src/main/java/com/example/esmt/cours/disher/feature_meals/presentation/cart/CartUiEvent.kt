package com.example.esmt.cours.disher.feature_meals.presentation.cart

import com.example.esmt.cours.disher.feature_meals.domain.model.CartItem
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

sealed class CartUiEvent {

    object PopBackStack: CartUiEvent()
    data class Navigate(val route: String): CartUiEvent()
    data class ShowMealDetails(val id: Int): CartUiEvent()
    data class RemoveMealFromCart(val cartItem: CartItem): CartUiEvent()
    data class UpdateCartItemQuantity(val cartItem: CartItem, val isIncrement: Boolean): CartUiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): CartUiEvent()

}