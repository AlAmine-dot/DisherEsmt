package com.example.esmt.cours.disher.feature_meals.domain.model

import com.example.esmt.cours.disher.feature_meals.data.local.entities.CategoryEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.MealEntity
import kotlinx.serialization.SerialName

data class Category(

    val categoryId: Int,
    val categoryName: String,
    val categoryThumb: String,
    val categoryDescription: String,
    // Pour le moment je laisse 5 en attendant d'avoir un bon algo
    val ratings: Double = 5.0

) {
    fun toCategoryEntity(): CategoryEntity {
        return CategoryEntity(
            idCategory = categoryId,
            categoryName = categoryName,
            categoryThumb=categoryThumb,
            categoryDescription = categoryDescription
        )
    }
//    override fun toString(): String {
//        return "Category(categoryName='$categoryName', categoryThumb='$categoryThumb', categoryDescription='$categoryDescription')"
//    }
    override fun toString(): String {
        return "Category(categoryName='$categoryName')"
    }
}
