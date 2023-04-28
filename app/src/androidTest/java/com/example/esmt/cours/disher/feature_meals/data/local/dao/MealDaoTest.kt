package com.example.esmt.cours.disher.feature_meals.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.esmt.cours.disher.core.data.local.DisherDatabase
import com.example.esmt.cours.disher.feature_meals.data.local.entities.MealEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class MealDaoTest {

    private lateinit var database: DisherDatabase
    private lateinit var dao: MealsDao

    private val meal1 = MealEntity(
        mealId = 1,
        strMeal = "Meal 1",
        strMealThumb = "thumb1.jpg",
        strCategory = "Test Category",
        strArea = "Test Area",
        strInstructions = "Test instructions",
        strYoutube = "https://www.youtube.com/watch?v=test",
        ingredients = listOf("ingredient1", "ingredient2"),
        measures = listOf("measure1", "measure2")
    )

    private val meal2 = MealEntity(
        mealId = 2,
        strMeal = "Meal 2",
        strMealThumb = "thumb2.jpg",
        strCategory = "Test Category",
        strArea = "Test Area",
        strInstructions = "Test instructions",
        strYoutube = "https://www.youtube.com/watch?v=test",
        ingredients = listOf("ingredient1", "ingredient2"),
        measures = listOf("measure1", "measure2")
    )

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DisherDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.mealsDao()
    }

    @After
    fun teardown(){
        database.close()
    }

//    @Transaction
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addMeals(meals: List<MealEntity>)

    @Test
    fun addMeals() = runTest {
        val mealsList = listOf(meal1, meal2)
        dao.addMeals(mealsList)

        // Get all meals from database
        val meals = dao.getAllMeals()

        // Check that the retrieved meals match the added meals
        assertThat(meals).hasSize(2)
        assertThat(meals[0]).isEqualTo(meal1)
        assertThat(meals[1]).isEqualTo(meal2)

    }

    @Test
    fun getAllMealsByCategory() = runTest {
        val mealsList = listOf(meal1, meal2)
        dao.addMeals(mealsList)

        // Get all meals by category from database
        val meals = dao.getAllMealsByCategory("Test Category")

        // Check that the retrieved meals match the added meals
        assertThat(meals).hasSize(2)
        assertThat(meals[0]).isEqualTo(meal1)
        assertThat(meals[1]).isEqualTo(meal2)
    }

    @Test
    fun deleteAllMealsOfCategory() = runTest {
        val mealsList = listOf(meal1, meal2)
        dao.addMeals(mealsList)

        dao.deleteAllMealsOfCategory("Test Category")

        // Get all meals from database
        val meals = dao.getAllMeals()

        // Check that the retrieved meals match the expected result
        assertThat(meals).hasSize(0)
    }

    @Test
    fun deleteAllMeals() = runTest {
        // Add meals to database
        dao.addMeals(listOf(meal1, meal2))

        // Delete all meals
        dao.deleteAllMeals()

        // Get all meals from database
        val meals = dao.getAllMeals()

        // Check that no meals were retrieved
        assertThat(meals).isEmpty()
    }

    @Test
    fun getMealById() = runTest {
        // Insert a meal into the database
        dao.addMeals(listOf(meal1))

        // Get the meal by id
        val retrievedMeal = dao.getMealById(meal1.mealId)

        // Check that the retrieved meal matches the added meal
        assertThat(retrievedMeal).isEqualTo(meal1)
    }

    @Test
    fun searchMealByName() = runTest {
        val mealsList = listOf(meal1, meal2)
        dao.addMeals(mealsList)

        // Search for a meal by name
        val searchedMeals = dao.searchMealByName("Meal 1")

        // Check that the retrieved meals match the search query
        assertThat(searchedMeals).hasSize(1)
        assertThat(searchedMeals[0]).isEqualTo(meal1)
    }


}