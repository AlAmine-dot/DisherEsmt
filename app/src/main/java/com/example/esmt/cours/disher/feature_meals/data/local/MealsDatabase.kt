package com.example.esmt.cours.disher.feature_meals.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.esmt.cours.disher.feature_meals.data.local.dao.CategoriesDao
import com.example.esmt.cours.disher.feature_meals.data.local.dao.FavoriteMealsDao
import com.example.esmt.cours.disher.feature_meals.data.local.dao.MealsDao
import com.example.esmt.cours.disher.feature_meals.data.local.entities.CategoryEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.FavoriteMealItemEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.MealEntity

@Database(entities = [FavoriteMealItemEntity::class, MealEntity::class, CategoryEntity::class], version = 3)
@TypeConverters(ListConverter::class)
abstract class MealsDatabase: RoomDatabase() {

    abstract fun mealsDao(): MealsDao
    abstract fun categoriesDao() : CategoriesDao
    abstract fun favoriteMealsDao(): FavoriteMealsDao

    companion object {
        @Volatile
        private var INSTANCE: MealsDatabase? = null

        fun getInstance(context: Context): MealsDatabase{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MealsDatabase::class.java,
                    "disher_db"
                ).build().also{
                    INSTANCE = it
                }
            }
        }
    }

}