package com.example.esmt.cours.disher.feature_meals.data.remote.dto

import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesDTO(
    @SerialName("categories") val categories: List<CategoryItemDTO> = emptyList()
){

    @Serializable
    data class CategoryItemDTO(
        @SerialName("idCategory") val idCategory: Int,
        @SerialName("strCategory") val strCategory: String? = null,
        @SerialName("strCategoryThumb") val strCategoryThumb: String? = null,
        @SerialName("strCategoryDescription") val strCategoryDescription: String? = null
    ){
        fun toCategory(): Category {
            return Category(
                categoryId = idCategory,
                categoryName = strCategory?: "",
                categoryThumb = strCategoryThumb?: "",
                categoryDescription = strCategoryDescription?: ""
            )
        }
    }
}
