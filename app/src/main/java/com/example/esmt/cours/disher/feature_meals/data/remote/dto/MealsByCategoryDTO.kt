package com.example.esmt.cours.disher.feature_meals.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealsByCategoryDTO(
    @SerialName("meals") val meals: List<MealDTO> = emptyList()
){
    @Serializable
    data class MealDTO(
        @SerialName("idMeal") val idMeal: Int,
        @SerialName("strMeal") val strMeal: String,
        @SerialName("strMealThumb") val strMealThumb: String? = null
    ){
//        fun toMeal(): Meal {
//            return Meal(
//                idMeal = idMeal,
//                mealName = strMeal,
//                mealThumb = strMeal
//            )
//        }

//        fun toDetailedMeal(): DetailedMeal {
//            return
//        }
//        fun toCategory(): Category {
//            return Category(
//                categoryName = strCategory?: "",
//                categoryThumb = strCategoryThumb?: "",
//                categoryDescription = strCategoryDescription?: ""
//            )
//        }
    }
}