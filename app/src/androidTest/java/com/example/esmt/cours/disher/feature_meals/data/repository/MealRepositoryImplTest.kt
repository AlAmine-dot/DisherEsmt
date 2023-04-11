//package com.example.esmt.cours.disher.feature_meals.data.repository
//
//import com.example.esmt.cours.disher.feature_meals.data.remote.dto.CategoriesDTO
//import com.example.esmt.cours.disher.feature_meals.data.service.MealService
//import io.mockk.coEvery
//import org.junit.Before
//import io.mockk.mockk
//import junit.framework.TestCase.assertEquals
//import kotlinx.coroutines.runBlocking
//import org.junit.Test
//
//class MealRepositoryImplTest {
//
//    private lateinit var mealRepositoryImpl: MealRepositoryImpl
//    private lateinit var mealService: MealService
//
//    @Before
//    fun setUp() {
//        mealService = mockk(relaxed = true)
//        mealRepositoryImpl = MealRepositoryImpl(mealService)
//    }
//
//    @Test
//    fun TestGetAllCategoriesReturnsExpectedCategoriesDTO() = runBlocking {
//        // Given
//        val categories = listOf(
//            CategoriesDTO.CategoryItemDTO(
//                1,
//                "Category thumb 1",
//                "Category description 1"
//            ),
//            CategoriesDTO.CategoryItemDTO(2, "Category thumb 2","Category description 2")
//        )
//        coEvery { mealService.getAllCategories() } returns categories
//
//        // When
//        val result = mealRepositoryImpl.getAllCategories()
//
//        // Then
//        assertEquals(categories, result)
//    }
//}