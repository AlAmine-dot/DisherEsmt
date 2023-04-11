package com.example.esmt.cours.disher.feature_meals.domain.model

import com.example.esmt.cours.disher.feature_meals.data.local.entities.MealEntity


data class Meal(
    val id: Int,
    val dateModified: String?,
    val strCreativeCommonsConfirmed: String?,
    val strDrinkAlternate: String?,
    val strImageSource: String?,
    val strArea: String?,
    val strCategory: String?,
    val strInstructions: String?,
    val strMealName: String?,
    val strMealThumb: String?,
    val strSource: String?,
    val strTags: String?,
    val strYoutube: String?,
    val ingredients: List<String>,
    val measures: List<String>

){
    fun toMealEntity(): MealEntity {
        return MealEntity(
            mealId = this.id,
            dateModified = this.dateModified,
            strCreativeCommonsConfirmed = this.strCreativeCommonsConfirmed,
            strDrinkAlternate = this.strDrinkAlternate,
            strImageSource = this.strImageSource,
            strArea = this.strArea,
            strCategory = this.strCategory,
            strInstructions = this.strInstructions,
            strMeal = this.strMealName,
            strMealThumb = this.strMealThumb,
            strSource = this.strSource,
            strTags = this.strTags,
            strYoutube = this.strYoutube,
            ingredients = this.ingredients,
            measures = this.measures
        )
    }

    override fun toString(): String {
        return "Meal(id=$id, dateModified=$dateModified, strCreativeCommonsConfirmed=$strCreativeCommonsConfirmed, strDrinkAlternate=$strDrinkAlternate, strImageSource=$strImageSource, strArea=$strArea, strCategory=$strCategory, strInstructions=$strInstructions, strMealName=$strMealName, strMealThumb=$strMealThumb, strSource=$strSource, strTags=$strTags, strYoutube=$strYoutube, ingredients=$ingredients, measures=$measures)"
    }


}