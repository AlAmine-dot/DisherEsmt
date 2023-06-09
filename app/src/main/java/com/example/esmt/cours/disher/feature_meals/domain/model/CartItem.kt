package com.example.esmt.cours.disher.feature_meals.domain.model

import com.example.esmt.cours.disher.feature_meals.data.local.entities.CartItemEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.CategoryEntity

data class CartItem (

    val cartItemId: Int,
    val cartItemMeal: Meal,
    var cartItemQuantity: Int,

    ) {

    fun updateCartItemQuantity(newValue: Int){
        this.cartItemQuantity = newValue
    }

    fun incrementQuantity(){
        this.cartItemQuantity = cartItemQuantity + 1
    }

    fun decrementQuantity(){
        if(cartItemQuantity > 1){
            this.cartItemQuantity = cartItemQuantity - 1
        }
    }

    fun toCartItemEntity() : CartItemEntity {
        return CartItemEntity(
            cartMealItemId = cartItemId,
            cartMeal = cartItemMeal.toMealEntity(),
            cartMealItemQuantity = cartItemQuantity,
        )
    }

    override fun toString(): String {
        return "CartItem(cartItemId=$cartItemId, cartItemMeal=$cartItemMeal, cartItemQuantity=$cartItemQuantity)"
    }

}