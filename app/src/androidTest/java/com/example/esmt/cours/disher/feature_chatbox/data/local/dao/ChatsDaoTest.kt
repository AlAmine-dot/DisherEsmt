package com.example.esmt.cours.disher.feature_chatbox.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.esmt.cours.disher.core.data.local.DisherDatabase
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.ChatConfig
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.SenderLabel
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.dateFormatter
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.timeFormatter
import com.example.esmt.cours.disher.feature_meals.data.local.entities.MealEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class ChatsDaoTest {

    private lateinit var database: DisherDatabase
    private lateinit var dao: ChatsDao

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
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DisherDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.chatsDao()
    }

    @After
    fun teardown(){
        database.close()
    }


    @Test
    fun addChat() = runTest {
        val chatA = chat1
        val chatB = chat2
        dao.insertChat(chatA)
        dao.insertChat(chatB)

        // Get all chats
        val chats = dao.getChats()

        // Check if match
        assertThat(chats).hasSize(2)
        assertThat(chats.first()).isEqualTo(chatA)
    }

    @Test
    fun removeChat() = runTest {
        val chatA = chat1
        val chatB = chat2
        dao.insertChat(chatA)
        dao.insertChat(chatB)

        // Get all chats before removing
        val chatsBefore = dao.getChats()

        // Remove chat
        dao.removeChat(chatA)

        // Get all chats after removing
        val chatsAfter = dao.getChats()

        // Check if match
        assertThat(chatsBefore).contains(chatA)
        assertThat(chatsBefore).contains(chatB)
        assertThat(chatsAfter).doesNotContain(chatA)
        assertThat(chatsAfter).contains(chatB)
    }

    @Test
    fun deleteAllChats() = runTest {
        val chatA = chat1
        val chatB = chat2
        dao.insertChat(chatA)
        dao.insertChat(chatB)

        // Get all chats before removing ALL
        val chatsBefore = dao.getChats()

        // Remove ALL
        dao.removeAllChats()

        // Get all chats after removing ALL
        val chatsAfter = dao.getChats()

        // Check if match
        assertThat(chatsBefore).hasSize(2)
        assertThat(chatsAfter).hasSize(0)
    }

    @Test
    fun getRecentChats_shouldReturnSixRecentChats() = runTest {
        // Add more chats to the database
        val chatA = chat1

        val chatB = chat2

        val chatC = Chat(
            id = 3,
            text = "I want to order some sushi for delivery",
            senderLabel = SenderLabel.HUMAN_SENDER_LABEL,
            dateSent = dateFormatter.format(Date(System.currentTimeMillis() - 1000 * 60 * 5)),
            timeSent = timeFormatter.format(Date(System.currentTimeMillis() - 1000 * 60 * 5).time),
            conversationName = ChatConfig.DEFAULT_CONVO_NAME
        )
        val chatD = Chat(
            id = 4,
            text = "Do you have any vegetarian options ?",
            senderLabel = SenderLabel.HUMAN_SENDER_LABEL,
            dateSent = dateFormatter.format(Date(System.currentTimeMillis() - 1000 * 60 * 4)),
            timeSent = timeFormatter.format(Date(System.currentTimeMillis() - 1000 * 60 * 4).time),
            conversationName = ChatConfig.DEFAULT_CONVO_NAME
        )
        val chatE = Chat(
            id = 5,
            text = "Yes, we have a vegetable roll and a tofu roll",
            senderLabel = SenderLabel.CHATGPT_SENDER_LABEL,
            dateSent = dateFormatter.format(Date(System.currentTimeMillis() - 1000 * 60 * 3)),
            timeSent = timeFormatter.format(Date(System.currentTimeMillis() - 1000 * 60 * 3).time),
            conversationName = ChatConfig.DEFAULT_CONVO_NAME
        )
        val chatF = Chat(
            id = 6,
            text = "I will take the vegetable roll please",
            senderLabel = SenderLabel.HUMAN_SENDER_LABEL,
            dateSent = dateFormatter.format(Date(System.currentTimeMillis() - 1000 * 60 * 2)),
            timeSent = timeFormatter.format(Date(System.currentTimeMillis() - 1000 * 60 * 2).time),
            conversationName = ChatConfig.DEFAULT_CONVO_NAME
        )

        dao.insertChat(chatA)
        dao.insertChat(chatB)
        dao.insertChat(chatC)
        dao.insertChat(chatD)
        dao.insertChat(chatE)
        dao.insertChat(chatF)

        // Retrieve the 6 most recent chats
        val recentChats = dao.getRecentChats()

        // Check if exactly 6 chats are returned
        assertThat(recentChats).hasSize(6)

        // Check if the chats are returned in the correct order
        assertThat(recentChats).containsExactly(chatF, chatE, chatD, chatC, chat2, chat1)
    }


//
//    @Test
//    fun addMeals() = runTest {
//        val mealsList = listOf(meal1, meal2)
//        dao.addMeals(mealsList)
//
//        // Get all meals from database
//        val meals = dao.getAllMeals()
//
//        // Check that the retrieved meals match the added meals
//        assertThat(meals).hasSize(2)
//        assertThat(meals[0]).isEqualTo(meal1)
//        assertThat(meals[1]).isEqualTo(meal2)
//
//    }

//    @Test
//    fun getAllMealsByCategory() = runTest {
//        val mealsList = listOf(meal1, meal2)
//        dao.addMeals(mealsList)
//
//        // Get all meals by category from database
//        val meals = dao.getAllMealsByCategory("Test Category")
//
//        // Check that the retrieved meals match the added meals
//        assertThat(meals).hasSize(2)
//        assertThat(meals[0]).isEqualTo(meal1)
//        assertThat(meals[1]).isEqualTo(meal2)
//    }
//
//    @Test
//    fun deleteAllMealsOfCategory() = runTest {
//        val mealsList = listOf(meal1, meal2)
//        dao.addMeals(mealsList)
//
//        dao.deleteAllMealsOfCategory("Test Category")
//
//        // Get all meals from database
//        val meals = dao.getAllMeals()
//
//        // Check that the retrieved meals match the expected result
//        assertThat(meals).hasSize(0)
//    }
//
//    @Test
//    fun deleteAllMeals() = runTest {
//        // Add meals to database
//        dao.addMeals(listOf(meal1, meal2))
//
//        // Delete all meals
//        dao.deleteAllMeals()
//
//        // Get all meals from database
//        val meals = dao.getAllMeals()
//
//        // Check that no meals were retrieved
//        assertThat(meals).isEmpty()
//    }
//
//    @Test
//    fun getMealById() = runTest {
//        // Insert a meal into the database
//        dao.addMeals(listOf(meal1))
//
//        // Get the meal by id
//        val retrievedMeal = dao.getMealById(meal1.mealId)
//
//        // Check that the retrieved meal matches the added meal
//        assertThat(retrievedMeal).isEqualTo(meal1)
//    }
//
//    @Test
//    fun searchMealByName() = runTest {
//        val mealsList = listOf(meal1, meal2)
//        dao.addMeals(mealsList)
//
//        // Search for a meal by name
//        val searchedMeals = dao.searchMealByName("Meal 1")
//
//        // Check that the retrieved meals match the search query
//        assertThat(searchedMeals).hasSize(1)
//        assertThat(searchedMeals[0]).isEqualTo(meal1)
//    }
//

}