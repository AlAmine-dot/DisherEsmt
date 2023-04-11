package com.example.esmt.cours.disher.feature_meals.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.esmt.cours.disher.core.util.Constants
import com.example.esmt.cours.disher.feature_meals.domain.model.Category

@Entity(tableName = Constants.CATEGORIES_TABLE)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false)
    val idCategory: Int,
    val categoryName: String,
    val categoryThumb: String,
    val categoryDescription: String,
){
    fun toCategory(): Category{
        return Category(
            categoryId = idCategory,
            categoryName = categoryName,
            categoryThumb = categoryThumb,
            categoryDescription = categoryDescription
        )
    }
}

