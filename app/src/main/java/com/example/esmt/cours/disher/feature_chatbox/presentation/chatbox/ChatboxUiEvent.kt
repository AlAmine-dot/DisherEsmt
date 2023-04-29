package com.example.esmt.cours.disher.feature_chatbox.presentation.chatbox

import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

sealed class ChatboxUiEvent {

    object PopBackStack: ChatboxUiEvent()
    data class Navigate(val route: String): ChatboxUiEvent()
    data class PromptChatAssistant(val prompt: String): ChatboxUiEvent()
//    data class RemoveMealFromFavorites(val meal: Meal): ChatboxUiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): ChatboxUiEvent()

}