package com.example.esmt.cours.disher.feature_meals.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.esmt.cours.disher.core.util.Constants

@Entity(tableName = Constants.FAVORITE_MEALS_TABLE)
data class FavoriteMealItemEntity(
    @PrimaryKey(autoGenerate = true)
    val FavoriteMealItemId: Int,
    @Embedded
    val FavoriteMeal: MealEntity
)
