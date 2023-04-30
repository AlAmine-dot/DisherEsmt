package com.example.esmt.cours.disher.feature_chatbox.presentation.chatbox

import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.CategoryFeature

data class ChatboxUiState (

    val isLoading: Boolean = false,
    val isTyping: Boolean = false,
    val mainPromptField: String = "",
    private var chatMessages: List<Chat> = emptyList(),
    val error: String = "",

    ){

    fun addChatMessage(chat: Chat) {
        val newList = chatMessages.toMutableList()
        newList.add(chat)
        chatMessages = newList.toList()
    }

    fun replaceLastChatMessage(chat: Chat) {
        if (chatMessages.isNotEmpty()) {
            val lastChatMessage = chatMessages.last()
            if (lastChatMessage.text == "...") {
                val newList = chatMessages.toMutableList()
                newList[newList.lastIndex] = chat
                chatMessages = newList.toList()
            }
        }
    }

    fun getChatMessages(): List<Chat> {
        return chatMessages
    }

    override fun toString(): String {
        return "ChatboxUiState(isLoading=$isLoading, isTyping=$isTyping, chatMessages=$chatMessages, error='$error')"
    }


}
