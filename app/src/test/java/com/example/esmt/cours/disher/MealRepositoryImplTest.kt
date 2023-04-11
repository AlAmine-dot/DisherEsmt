package com.example.esmt.cours.disher

import com.example.esmt.cours.disher.feature_meals.data.remote.dto.CategoriesDTO
import com.example.esmt.cours.disher.feature_meals.data.repository.MealRepositoryImpl
import com.example.esmt.cours.disher.feature_meals.data.service.MealService
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import io.mockk.coEvery
import io.mockk.coVerify
import org.junit.Before
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MealRepositoryImplTest {

    private lateinit var mealRepositoryImpl: MealRepositoryImpl
    private lateinit var mealService: MealService

    // Given
    val category = Category("Seafood", "seafood.jpg", "category descr")
    val meals = listOf(
        Meal(
            1,
            "2021-03-25T10:50:01.783Z",
            "Yes",
            null,
            "https://www.themealdb.com/images/media/meals/xxxyyy1234567890.jpg",
            "Canadian",
            "Seafood",
            "Instructions for the Seafood meal",
            "Seafood Meal",
            "https://www.themealdb.com/images/media/meals/xxxyyy1234567890.jpg",
            null,
            "sea, dish",
            "https://www.youtube.com/watch?v=xxx_yyy",
            listOf("Ingredient 1", "Ingredient 2"),
            listOf("1 cup", "2 tbsp")
        ),
        Meal(
            2,
            "2021-03-25T10:50:01.783Z",
            "Yes",
            null,
            "https://www.themealdb.com/images/media/meals/xxxyyy1234567891.jpg",
            "Canadian",
            "Seafood",
            "Instructions for the Seafood meal 2",
            "Seafood Meal 2",
            "https://www.themealdb.com/images/media/meals/xxxyyy1234567891.jpg",
            null,
            "sea, dish",
            "https://www.youtube.com/watch?v=xxx_yyy_2",
            listOf("Ingredient 1", "Ingredient 3"),
            listOf("1 cup", "2 tbsp")
        )
    )

    @Before
    fun setUp() {
        mealService = mockk(relaxed = true)
        mealRepositoryImpl = MealRepositoryImpl(mealService)

    }

    @Test
    fun `Test get all categories returns expected CategoriesDTO converted to Category model`() = runBlocking {
        // Given
        val categories = listOf(
            CategoriesDTO.CategoryItemDTO(
                1,
                "Category thumb 1",
                "Category description 1"
            ),
            CategoriesDTO.CategoryItemDTO(2, "Category thumb 2","Category description 2")
        )
        coEvery { mealService.getAllCategoriesFromRemote() } returns categories

        // When
        val result = mealRepositoryImpl.getAllCategoriesFromRemote()

        // Then
        assertEquals(categories.map{ it.toCategory() }, result)
    }

    @Test
    fun `Test get all meals by category from remote returns expected meals`() = runBlocking {

        coEvery { mealService.getMealsByCategoryFromRemote(category.categoryName) } returns meals

        // When
        val result = mealRepositoryImpl.getAllMealsByCategoryFromRemote(category)

        // Then
        assertEquals(meals, result)
    }

    @Test
    fun `Test get all meals by category from local source returns expected meals`() = runBlocking {
        // Given

        coEvery { mealService.getMealsByCategoryFromLocalSource(category.categoryName) } returns meals

        // When
        val result = mealRepositoryImpl.getAllMealsByCategoryFromLocalSource(category)

        // Then
        assertEquals(meals, result)
    }

    @Test
    fun `Test get detailed meal by ID from remote returns expected meal`() = runBlocking {
        // Given
        val mealId = meals.first().id
        val meal = meals.first()
        coEvery { mealService.getMealByIdFromRemote(mealId) } returns meals.first()

        // When
        val result = mealRepositoryImpl.getDetailedMealByIdFromRemote(mealId)

        // Then
        assertEquals(meal, result)
    }

    @Test
    fun `Test get detailed meal by ID from localSource returns expected meal`() = runBlocking {
        // Given
        val mealId = meals.first().id
        val expectedMeal = meals.first()
        mealService.addMealsToLocalSource(meals.map { it.toMealEntity() })
        coEvery { mealService.getMealByIdFromLocalSource(mealId) } returns meals.first().toMealEntity()

        // When
//        Log.d("testings id",mealId.toString())
//        Log.d("testings excepted",expectedMeal.toString())
        val result = mealRepositoryImpl.getDetailedMealByIdFromLocalSource(mealId)
//        Log.d("testings result",result.toString())

        // Then
        assertEquals(expectedMeal.id, result?.id)
        assertEquals(expectedMeal.dateModified, result?.dateModified)
        assertEquals(expectedMeal.strCreativeCommonsConfirmed, result?.strCreativeCommonsConfirmed)
        // comparer les autres attributs ici ...
    }

    @Test
    fun `test searchMealFromRemote`() = runBlocking {
        // Définir les paramètres et les valeurs de retour attendus pour le mock
        val name = meals.first().strMealName.orEmpty()
        val mealList = listOf(meals.first())

        coEvery { mealService.searchMealFromRemote(name) } returns mealList

        // Appeler la méthode à tester
        val result = mealRepositoryImpl.searchMealFromRemote(name)

        // Vérifier que la méthode a retourné la liste de repas attendue
        assertEquals(mealList, result)

    }

    fun `test searchMealLocalSource`() = runBlocking {
        // Définir les paramètres et les valeurs de retour attendus pour le mock
        val name = meals.first().strMealName.orEmpty()
        val mealList = listOf(meals.first())

        coEvery { mealService.searchMealFromLocalSource(name) } returns mealList

        // Appeler la méthode à tester
        val result = mealRepositoryImpl.searchMealFromLocalSource(name)

        // Vérifier que la méthode a retourné la liste de repas attendue
        assertEquals(mealList, result)

    }

    @Test
    fun `Test addMealsToLocalSource adds meals to local source`() = runBlocking {
        // Given

        val mealEntities = meals.map { it.toMealEntity() }
        coEvery { mealService.addMealsToLocalSource(mealEntities) } returns Unit

        // When
        mealRepositoryImpl.addMealsToLocalSource(meals)

        // Then
        coVerify { mealService.addMealsToLocalSource(mealEntities) }
    }

    @Test
    fun `Test deleteAllMealsFromLocalSource deletes all meals from local source`() = runBlocking {
        // Given

        val mealEntities = meals.map { it.toMealEntity() }
        coEvery { mealService.removeAllMealsFromLocalSource() } returns Unit

        // When
        mealRepositoryImpl.deleteAllMealsFromLocalSource()

        // Then
        coVerify { mealService.removeAllMealsFromLocalSource() }
    }



}