package com.example.esmt.cours.disher.feature_chatbox.presentation.chatbox

import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

data class ChatboxUiState (

    val isLoading: Boolean = false,
    val chatMessages: List<Chat> = emptyList(),
    val error: String = "",

    ){

    override fun toString(): String {
        return "ChatboxUiState(isLoading=$isLoading, chatMessages=$chatMessages, error='$error')"
    }
}
