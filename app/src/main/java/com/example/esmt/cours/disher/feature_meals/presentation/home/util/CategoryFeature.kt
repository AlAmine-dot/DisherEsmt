package com.example.esmt.cours.disher.feature_meals.presentation.home.util

import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

data class CategoryFeature(
    val featureTitle: String = "",
    val category: Category?,
    val featuredMeals: List<Meal> = emptyList(),
    val emojis: List<String> = emptyList(),
)
