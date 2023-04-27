package com.example.esmt.cours.disher.feature_meals.presentation.home

import com.example.esmt.cours.disher.feature_meals.domain.model.CartItem
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.AlertDialogState
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.CategoryFeature

data class HomeUiState(
    val isLoading: Boolean = false,

    private var categoryFeatures: List<CategoryFeature> = emptyList(),
    val userCart: List<CartItem> = emptyList(),
    val alertDialogState: AlertDialogState = AlertDialogState(),
    val forYouMeals: CategoryFeature? = null,
    val feedModeOption: FeedMode = FeedMode.DISCOVERY,
    val swiperContent: CategoryFeature? = null,
    val error: String = "",

    ){

    fun addCategoryFeature(categoryFeature: CategoryFeature) {
        val newList = categoryFeatures.toMutableList()
        newList.add(categoryFeature)
        categoryFeatures = newList.toList()
    }

    fun getCategoryFeatures(): List<CategoryFeature> {
        return categoryFeatures
    }

    companion object {
        const val MEALS_PAGE_SIZE: Int = 6
    }

    override fun toString(): String {
        return "HomeState(isLoading=$isLoading, categoryFeatures=$categoryFeatures, error='$error')"
    }


}

enum class FeedMode {
    DISCOVERY,
    CUSTOM
}