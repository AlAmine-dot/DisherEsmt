package com.example.esmt.cours.disher.feature_chatbox.domain.repository

import androidx.compose.runtime.MutableState
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Conversation

interface ChatRepository {

    suspend fun getChatGptResponse(
        lastSixChats: List<Chat>,
        userPrompt: String,
        // Que représente system concrétement ?
        system: MutableState<String>,
    ): String

    suspend fun getChats(): List<Chat>

    suspend fun insertChat(chat: Chat)

    suspend fun getLastSixChat(convoName: String): List<Chat>

    suspend fun deleteChat(chat: Chat)

    suspend fun deleteAllChats()

    suspend fun insertConversation(convo: Conversation)

    suspend fun getConversations(): List<Conversation>

    suspend fun removeConversation(conversation: Conversation)



}