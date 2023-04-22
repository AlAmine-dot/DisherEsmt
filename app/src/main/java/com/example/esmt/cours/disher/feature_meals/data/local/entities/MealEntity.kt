package com.example.esmt.cours.disher.feature_meals.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.esmt.cours.disher.core.util.Constants
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

@Entity(tableName = Constants.MEALS_TABLE)
data class MealEntity(
    // Je sais qu'on pourrait restructurer cette entité et avoir par exemple une table ingredients
    // à part et tout mais là, je ne vais pas me casser la tête, je travaille tout seul, on verra
    // plus tard
    @PrimaryKey(autoGenerate = false)
    val mealId: Int,
    val dateModified: String? = null,
    val strCreativeCommonsConfirmed: String? = null,
    val strDrinkAlternate: String? = null,
    val strImageSource: String? = null,
    val strArea: String? = null,
    val strCategory: String? = null,
    val strInstructions: String? = null,
    val strMeal: String? = null,
    val strMealThumb: String? = null,
    val strSource: String? = null,
    val strTags: String? = null,
    val strYoutube: String? = null,
    val isFavorite: Boolean = false,
    val isIntoCart: Boolean = false,
    @ColumnInfo(name = "ingredients") val ingredients: List<String>,
    @ColumnInfo(name = "measures") val measures: List<String>

) {
    fun toMeal(): Meal {
        return Meal(
            id = mealId,
            strMealName = strMeal ?: "",
            strArea = strArea ?: "",
            strCategory = strCategory ?: "",
            strInstructions = strInstructions ?: "",
            strMealThumb = strMealThumb ?: "",
            strYoutube = strYoutube ?: "",
            dateModified = dateModified ?: "",
            strCreativeCommonsConfirmed = strCreativeCommonsConfirmed ?: "",
            strDrinkAlternate = strDrinkAlternate ?: "",
            strImageSource = strImageSource ?: "",
            strTags = strTags ?: "",
            strSource = strSource ?: "",
            ingredients = ingredients,
            measures = measures,
            isFavorite = isFavorite,
            isIntoCart = isIntoCart
        )
    }
}