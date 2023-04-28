package com.example.esmt.cours.disher.feature_meals.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.esmt.cours.disher.core.common.util.Constants
import com.example.esmt.cours.disher.feature_meals.domain.model.CartItem

@Entity(tableName = Constants.CART_MEALS_TABLE)
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val cartMealItemId: Int,
    val cartMealItemQuantity: Int,
    @Embedded
    val cartMeal: MealEntity
){
    fun toCartItem() : CartItem{
        return CartItem(
            cartItemId = cartMealItemId,
            cartItemMeal = cartMeal.toMeal(),
            cartItemQuantity = cartMealItemQuantity
        )
    }
}
