package com.example.esmt.cours.disher.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.esmt.cours.disher.feature_chatbox.data.local.dao.ChatsDao
import com.example.esmt.cours.disher.feature_chatbox.data.local.dao.ConversationsDao
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Conversation
import com.example.esmt.cours.disher.feature_meals.data.local.ListConverter
import com.example.esmt.cours.disher.feature_meals.data.local.dao.CartMealsDao
import com.example.esmt.cours.disher.feature_meals.data.local.dao.CategoriesDao
import com.example.esmt.cours.disher.feature_meals.data.local.dao.FavoriteMealsDao
import com.example.esmt.cours.disher.feature_meals.data.local.dao.MealsDao
import com.example.esmt.cours.disher.feature_meals.data.local.entities.CartItemEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.CategoryEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.FavoriteMealItemEntity
import com.example.esmt.cours.disher.feature_meals.data.local.entities.MealEntity

@Database(entities = [FavoriteMealItemEntity::class, MealEntity::class, CategoryEntity::class, CartItemEntity::class, Chat::class, Conversation::class], version = 8)
@TypeConverters(ListConverter::class)
abstract class DisherDatabase: RoomDatabase() {

    abstract fun mealsDao(): MealsDao
    abstract fun categoriesDao() : CategoriesDao
    abstract fun favoriteMealsDao(): FavoriteMealsDao
    abstract fun cartMealsDao(): CartMealsDao
    abstract fun conversationsDao(): ConversationsDao
    abstract fun chatsDao(): ChatsDao

    companion object {
        @Volatile
        private var INSTANCE: DisherDatabase? = null

        fun getInstance(context: Context): DisherDatabase {
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DisherDatabase::class.java,
                    "disher_db"
                ).build().also{
                    INSTANCE = it
                }
            }
        }
    }

}