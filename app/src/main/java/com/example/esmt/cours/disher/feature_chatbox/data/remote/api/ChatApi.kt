package com.example.esmt.cours.disher.feature_chatbox.data.remote.api

import androidx.compose.runtime.MutableState
import com.example.esmt.cours.disher.BuildConfig
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat
import com.example.esmt.cours.disher.feature_chatbox.data.remote.dto.CachedChatBot
import com.example.esmt.cours.disher.feature_chatbox.data.remote.dto.ChatBot
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.ChatConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface ChatApi {

    object ApiKey {
        var userApiKey = BuildConfig.API_KEY
    }

    companion object {
        suspend fun getChatGptResponse(
            lastSixChats: List<Chat>,
            userPrompt: String,
            // Que représente system concrétement ?
            system: MutableState<String>,
        ): String {
            var aiResponse: String
            try {
                withContext(Dispatchers.IO) {
                    val key = ApiKey.userApiKey
                    val request = ChatBot.ChatCompletionRequest(
                        model = ChatConfig.GPT_3_5_TURBO,
                        systemContent = system.value
                    )

                    val bot = CachedChatBot(
                        key,
                        request,
                        lastSixChats
                    )
                    aiResponse = bot.generateResponse(userPrompt)
                }
            } catch (e: SocketTimeoutException) {
                aiResponse = "Connection timed out. Please try again."
            } catch (e: java.lang.IllegalArgumentException) {
                aiResponse = "ERROR: ${e.message}"
            } catch (e: UnknownHostException) {
                aiResponse = "ERROR: ${e.message}.\n\n" +
                        "This could indicate no/very poor internet connection. " +
                        "Please check your connection and try again."
            }
            return aiResponse
        }
    }

}