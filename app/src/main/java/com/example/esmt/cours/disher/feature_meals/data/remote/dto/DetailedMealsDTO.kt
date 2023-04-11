package com.example.esmt.cours.disher.feature_meals.data.remote.dto

import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailedMealsDTO(
    val meals: List<DetailedMealDTO> = emptyList()
){

    @Serializable
    data class DetailedMealDTO(

        val idMeal: Int,
        @SerialName("dateModified") val dateModified: String? = null,
        @SerialName("strCreativeCommonsConfirmed") val strCreativeCommonsConfirmed: String? = null,
        @SerialName("strDrinkAlternate") val strDrinkAlternate: String? = null,
        @SerialName("strImageSource") val strImageSource: String? = null,
        val strArea: String? = null,
        val strCategory: String? = null,
        val strIngredient1: String? = null,
        val strIngredient10: String? = null,
        val strIngredient11: String? = null,
        val strIngredient12: String? = null,
        val strIngredient13: String? = null,
        val strIngredient14: String? = null,
        val strIngredient15: String? = null,
        val strIngredient16: String? = null,
        val strIngredient17: String? = null,
        val strIngredient18: String? = null,
        val strIngredient19: String? = null,
        val strIngredient2: String? = null,
        val strIngredient20: String? = null,
        val strIngredient3: String? = null,
        val strIngredient4: String? = null,
        val strIngredient5: String? = null,
        val strIngredient6: String? = null,
        val strIngredient7: String? = null,
        val strIngredient8: String? = null,
        val strIngredient9: String? = null,
        val strInstructions: String? = null,
        val strMeal: String? = null,
        val strMealThumb: String? = null,
        val strMeasure1: String? = null,
        val strMeasure10: String? = null,
        val strMeasure11: String? = null,
        val strMeasure12: String? = null,
        val strMeasure13: String? = null,
        val strMeasure14: String? = null,
        val strMeasure15: String? = null,
        val strMeasure16: String? = null,
        val strMeasure17: String? = null,
        val strMeasure18: String? = null,
        val strMeasure19: String? = null,
        val strMeasure2: String? = null,
        val strMeasure20: String? = null,
        val strMeasure3: String? = null,
        val strMeasure4: String? = null,
        val strMeasure5: String? = null,
        val strMeasure6: String? = null,
        val strMeasure7: String? = null,
        val strMeasure8: String? = null,
        val strMeasure9: String? = null,
        val strSource: String? = null,
        val strTags: String? = null,
        val strYoutube: String? = null

    ){
        fun toMeal(): Meal {
            return Meal(
                id = idMeal,
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
                ingredients = listOfNotNull(
                    strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
                    strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
                    strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
                    strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20
                ),
                measures = listOfNotNull(
                    strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
                    strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
                    strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
                    strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20
                )
            )
        }

    }

}