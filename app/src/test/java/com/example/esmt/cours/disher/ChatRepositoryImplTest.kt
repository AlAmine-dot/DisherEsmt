package com.example.esmt.cours.disher

import com.example.esmt.cours.disher.core.data.local.DisherDatabase
import com.example.esmt.cours.disher.feature_chatbox.data.local.dao.ChatsDao
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat
import com.example.esmt.cours.disher.feature_chatbox.data.repository.ChatRepositoryImpl
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.ChatConfig
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.SenderLabel
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.dateFormatter
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.timeFormatter
import com.example.esmt.cours.disher.feature_meals.data.remote.dto.CategoriesDTO
import com.example.esmt.cours.disher.feature_meals.data.repository.MealRepositoryImpl
import com.example.esmt.cours.disher.feature_meals.data.service.MealService
import com.example.esmt.cours.disher.feature_meals.domain.model.CartItem
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import io.mockk.coEvery
import io.mockk.coVerify
import org.junit.Before
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.Date

class ChatRepositoryImplTest {

    private lateinit var chatRepositoryImpl: ChatRepositoryImpl
    private lateinit var db: DisherDatabase
    private lateinit var chatsDao: ChatsDao

    // Given
    private val chat1 = Chat(
        id = 1,
        text = "Hello, how may I assist you today ?",
        senderLabel = SenderLabel.CHATGPT_SENDER_LABEL,
        dateSent = dateFormatter.format(Date()),
        timeSent = timeFormatter.format(Date().time),
        conversationName = ChatConfig.DEFAULT_CONVO_NAME
    )

    private val chat2 = Chat(
        id = 2,
        text = "Hi, I would like a pretty consistent meal without a lot of cooking",
        senderLabel = SenderLabel.HUMAN_SENDER_LABEL,
        dateSent = dateFormatter.format(Date()),
        timeSent = timeFormatter.format(Date().time),
        conversationName = ChatConfig.DEFAULT_CONVO_NAME
    )

    @Before
    fun setUp() {
        db = mockk(relaxed = true)
        chatRepositoryImpl = ChatRepositoryImpl(db)
        chatsDao = db.chatsDao()
    }

    @Test
    fun `Insert chat in database chats table`() = runBlocking {
        // Given
        val chatA = chat1
        val chatB = chat2

        coEvery { chatsDao.insertChat(chatA) }returns Unit

        // When
        chatRepositoryImpl.insertChat(chatA)

        // Then
        coVerify { chatsDao.insertChat(chatA) }
    }

    //    @Test
//    fun `Test addMealsToLocalSource adds meals to local source`() = runBlocking {
//        // Given
//
//        val mealEntities = meals.map { it.toMealEntity() }
//        coEvery { mealService.addMealsToLocalSource(mealEntities) } returns Unit
//
//        // When
//        mealRepositoryImpl.addMealsToLocalSource(meals)
//
//        // Then
//        coVerify { mealService.addMealsToLocalSource(mealEntities) }
//    }

//    @Test
//    fun `Test get all categories returns expected CategoriesDTO converted to Category model`() = runBlocking {
//        // Given
//        val categories = listOf(
//            CategoriesDTO.CategoryItemDTO(
//                1,
//                "Category thumb 1",
//                "Category description 1"
//            ),
//            CategoriesDTO.CategoryItemDTO(2, "Category thumb 2","Category description 2")
//        )
//        coEvery { mealService.getAllCategoriesFromRemote() } returns categories
//
//        // When
//        val result = mealRepositoryImpl.getAllCategoriesFromRemote()
//
//        // Then
//        assertEquals(categories.map{ it.toCategory() }, result)
//    }

//    @Test
//    fun `Test get all meals by category from remote returns expected meals`() = runBlocking {
//
//        coEvery { mealService.getMealsByCategoryFromRemote(category.categoryName) } returns meals
//
//        // When
//        val result = mealRepositoryImpl.getAllMealsByCategoryFromRemote(category)
//
//        // Then
//        assertEquals(meals, result)
//    }
//
//    @Test
//    fun `Test get all meals by category from local source returns expected meals`() = runBlocking {
//        // Given
//
//        coEvery { mealService.getMealsByCategoryFromLocalSource(category.categoryName) } returns meals
//
//        // When
//        val result = mealRepositoryImpl.getAllMealsByCategoryFromLocalSource(category)
//
//        // Then
//        assertEquals(meals, result)
//    }
//
//    @Test
//    fun `Test get detailed meal by ID from remote returns expected meal`() = runBlocking {
//        // Given
//        val mealId = meals.first().id
//        val meal = meals.first()
//        coEvery { mealService.getMealByIdFromRemote(mealId) } returns meals.first()
//
//        // When
//        val result = mealRepositoryImpl.getDetailedMealByIdFromRemote(mealId)
//
//        // Then
//        assertEquals(meal, result)
//    }
//
//    @Test
//    fun `Test get detailed meal by ID from localSource returns expected meal`() = runBlocking {
//        // Given
//        val mealId = meals.first().id
//        val expectedMeal = meals.first()
//        mealService.addMealsToLocalSource(meals.map { it.toMealEntity() })
//        coEvery { mealService.getMealByIdFromLocalSource(mealId) } returns meals.first().toMealEntity()
//
//        // When
////        Log.d("testings id",mealId.toString())
////        Log.d("testings excepted",expectedMeal.toString())
//        val result = mealRepositoryImpl.getDetailedMealByIdFromLocalSource(mealId)
////        Log.d("testings result",result.toString())
//
//        // Then
//        assertEquals(expectedMeal.id, result?.id)
//        assertEquals(expectedMeal.dateModified, result?.dateModified)
//        assertEquals(expectedMeal.strCreativeCommonsConfirmed, result?.strCreativeCommonsConfirmed)
//        // comparer les autres attributs ici ...
//    }
//
//    @Test
//    fun `test searchMealFromRemote`() = runBlocking {
//        // Définir les paramètres et les valeurs de retour attendus pour le mock
//        val name = meals.first().strMealName.orEmpty()
//        val mealList = listOf(meals.first())
//
//        coEvery { mealService.searchMealFromRemote(name) } returns mealList
//
//        // Appeler la méthode à tester
//        val result = mealRepositoryImpl.searchMealFromRemote(name)
//
//        // Vérifier que la méthode a retourné la liste de repas attendue
//        assertEquals(mealList, result)
//
//    }
//
//    fun `test searchMealLocalSource`() = runBlocking {
//        // Définir les paramètres et les valeurs de retour attendus pour le mock
//        val name = meals.first().strMealName.orEmpty()
//        val mealList = listOf(meals.first())
//
//        coEvery { mealService.searchMealFromLocalSource(name) } returns mealList
//
//        // Appeler la méthode à tester
//        val result = mealRepositoryImpl.searchMealFromLocalSource(name)
//
//        // Vérifier que la méthode a retourné la liste de repas attendue
//        assertEquals(mealList, result)
//
//    }
//
//    @Test
//    fun `Test addMealsToLocalSource adds meals to local source`() = runBlocking {
//        // Given
//
//        val mealEntities = meals.map { it.toMealEntity() }
//        coEvery { mealService.addMealsToLocalSource(mealEntities) } returns Unit
//
//        // When
//        mealRepositoryImpl.addMealsToLocalSource(meals)
//
//        // Then
//        coVerify { mealService.addMealsToLocalSource(mealEntities) }
//    }
//
//    @Test
//    fun `Test deleteAllMealsFromLocalSource deletes all meals from local source`() = runBlocking {
//        // Given
//
//        val mealEntities = meals.map { it.toMealEntity() }
//        coEvery { mealService.removeAllMealsFromLocalSource() } returns Unit
//
//        // When
//        mealRepositoryImpl.deleteAllMealsFromLocalSource()
//
//        // Then
//        coVerify { mealService.removeAllMealsFromLocalSource() }
//    }
//
//    @Test
//    fun testAddMealToCart() = runBlocking {
//        // Given
//        val meal = meals.first()
//        val mealEntity = meal.toMealEntity()
//        coEvery { mealService.addMealToCart(mealEntity) } returns Unit
//
//        // When
//        mealRepositoryImpl.addMealToCart(meal)
//
//        // Then
//        coVerify { mealService.addMealToCart(mealEntity) }
//    }
//
//    @Test
//    fun testRemoveMealFromCart() = runBlocking {
//        // Given
//        val meal = meals.first()
//        val mealEntity = meal.toMealEntity()
//        coEvery { mealService.removeMealFromCart(mealEntity) } returns Unit
//
//        // When
//        mealRepositoryImpl.removeMealFromCart(meal)
//
//        // Then
//        coVerify { mealService.removeMealFromCart(mealEntity) }
//    }
//
//    @Test
//    fun testGetCart() = runBlocking {
//        // Given
//        val cartItems = listOf(
//            CartItem(1,meals[0], 1),
//            CartItem(2, meals[1], 2)
//        )
//        coEvery { mealService.getCart() } returns cartItems
//
//        // When
//        val result = mealRepositoryImpl.getCart()
//
//        // Then
//        assertEquals(cartItems, result)
//        coVerify { mealService.getCart() }
//    }
//
//    @Test
//    fun testUpdateCartItemQuantity() = runBlocking {
//        // Given
//        val meal = meals.first()
//        val cartItem = CartItem(1, meal, 1)
//        val newQuantity = 2
//        coEvery { mealService.updateCartItemQuantity(meal.toMealEntity(), newQuantity) } returns Unit
//
//        // When
//        mealRepositoryImpl.updateCartItemQuantity(cartItem, newQuantity)
//
//        // Then
//        coVerify { mealService.updateCartItemQuantity(meal.toMealEntity(), newQuantity) }
//    }
//
//    @Test
//    fun testAddMealToFavorites() = runBlocking {
//        // Given
//        val meal = meals.first()
//        val mealEntity = meal.toMealEntity()
//        coEvery { mealService.addMealToFavorite(mealEntity) } returns Unit
//
//        // When
//        mealRepositoryImpl.addMealToFavorites(meal)
//
//        // Then
//        coVerify { mealService.addMealToFavorite(mealEntity) }
//    }
//
//    @Test
//    fun testRemoveMealFromFavorites() = runBlocking {
//        // Given
//        val meal = meals.first()
//        val mealEntity = meal.toMealEntity()
//        coEvery { mealService.removeMealFromFavorite(mealEntity) } returns Unit
//
//        // When
//        mealRepositoryImpl.removeMealFromFavorites(meal)
//
//        // Then
//        coVerify { mealService.removeMealFromFavorite(mealEntity) }
//    }
//
//    @Test
//    fun testGetFavoriteMeals() = runBlocking {
//        // Given
//        val favoriteMeals = listOf(meals[0], meals[1])
//        coEvery { mealService.getFavoriteMealsFromLocalSource() } returns favoriteMeals
//
//        // When
//        val result = mealRepositoryImpl.getFavoriteMeals()
//
//        // Then
//        assertEquals(favoriteMeals, result)
//        coVerify { mealService.getFavoriteMealsFromLocalSource() }
//    }


}