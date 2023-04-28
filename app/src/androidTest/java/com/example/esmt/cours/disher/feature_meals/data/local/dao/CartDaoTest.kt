package com.example.esmt.cours.disher.feature_meals.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.esmt.cours.disher.core.data.local.DisherDatabase
import com.example.esmt.cours.disher.feature_meals.data.local.entities.CartItemEntity
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
class CartDaoTest {

    private lateinit var database: DisherDatabase
    private lateinit var dao: CartMealsDao

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

    private val cartItem1 = CartItemEntity(
        cartMealItemId = 1,
        cartMealItemQuantity = 1,
        cartMeal = meal1
    )

    private val cartItem2 = CartItemEntity(
        cartMealItemId = 2,
        cartMealItemQuantity = 1,
        cartMeal = meal2
    )

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DisherDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.cartMealsDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun testAddMealToCartAndGetAllCartsMeal() = runTest {
        // When
        dao.addMealToCart(cartItem1)
        dao.addMealToCart(cartItem2)

        // Then
        val cartsMeal = dao.getAllCartsMeal()

        assertThat(cartsMeal).hasSize(2)
        assertThat(cartsMeal[0]).isEqualTo(cartItem1)
        assertThat(cartsMeal[1]).isEqualTo(cartItem2)
    }

    @Test
    fun testRemoveMealOfCartAndGetAllCartsMeal() = runTest {
        // Given
        dao.addMealToCart(cartItem1)
        dao.addMealToCart(cartItem2)

        // When
        dao.removeMealOfCart(2)

        // Then
        val cartsMeal = dao.getAllCartsMeal()

        assertThat(cartsMeal).hasSize(1)
        assertThat(cartsMeal[0]).isEqualTo(cartItem1)
    }

@Test
fun testIsMealIntoCart() = runTest {
    // When
    dao.addMealToCart(cartItem1)

    // Then
    val isMealIntoCart = dao.isMealIntoCart(cartItem1.cartMeal.mealId)
    assertThat(isMealIntoCart).isTrue()

    // When
    dao.removeMealOfCart(cartItem1.cartMeal.mealId)

    // Then
    val isMealIntoCartAfterRemoval = dao.isMealIntoCart(cartItem1.cartMeal.mealId)
    assertThat(isMealIntoCartAfterRemoval).isFalse()
}

@Test
fun testGetAllCartsMeal() = runTest {
    // Given
    val cartItem1 = CartItemEntity(1, 1, meal1)
    val cartItem2 = CartItemEntity(2, 2, meal2)
    dao.addMealToCart(cartItem1)
    dao.addMealToCart(cartItem2)

    // When
    val result = dao.getAllCartsMeal()

    // Then
    assertThat(result).containsExactly(cartItem1, cartItem2)
}


@Test
fun testUpdateQuantityAndGetAllCartsMeal() = runTest {
    // Given
    val initialQuantity = 2
    val newQuantity = 3
    val cartItem = CartItemEntity(3, initialQuantity, meal1)

    // When
    dao.addMealToCart(cartItem)
    dao.updateQuantity(cartItem.cartMeal.mealId, newQuantity)

    // Then
    val cartsMeal = dao.getAllCartsMeal()
    assertThat(cartsMeal).hasSize(1)

    val updatedCartItem = cartsMeal[0]
    assertThat(updatedCartItem.cartMealItemId).isEqualTo(cartItem.cartMealItemId)
    assertThat(updatedCartItem.cartMealItemQuantity).isEqualTo(newQuantity)
    assertThat(updatedCartItem.cartMeal).isEqualTo(meal1)
}

}