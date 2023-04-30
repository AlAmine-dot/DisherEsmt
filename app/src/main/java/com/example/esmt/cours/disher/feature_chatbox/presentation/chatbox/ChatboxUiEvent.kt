package com.example.esmt.cours.disher.feature_chatbox.presentation.chatbox

import androidx.compose.foundation.lazy.LazyListState
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import kotlinx.coroutines.CoroutineScope

sealed class ChatboxUiEvent {

    object PopBackStack: ChatboxUiEvent()
    data class Navigate(val route: String): ChatboxUiEvent()
    data class OnMainPromptTextChange(val newPrompt: String): ChatboxUiEvent()
    data class PromptChatAssistant(val prompt: String, val chatListState: LazyListState,val scope: CoroutineScope): ChatboxUiEvent()
//    data class RemoveMealFromFavorites(val meal: Meal): ChatboxUiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): ChatboxUiEvent()

}