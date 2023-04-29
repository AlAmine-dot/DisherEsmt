package com.example.esmt.cours.disher.feature_chatbox.domain.use_case

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat
import com.example.esmt.cours.disher.feature_chatbox.data.repository.ChatRepositoryImpl
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.ChatConfig
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.SenderLabel
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.dateFormatter
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.timeFormatter
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

class GenerateChatResponse @Inject constructor(
    private val repository: ChatRepositoryImpl
) {
    operator fun invoke(userChatRequest: Chat): Flow<Resource<Chat>> = flow {
        val userPrompt = userChatRequest.text
        // On émet le chargement
        emit(Resource.Loading())

        // On ajoute le nouveau chat de l'utilisateur au cache :
        repository.insertChat(userChatRequest)
        // Pour le moment je met des valeurs empty mais tu devra récupérer proprement
        // les last six chat et le state syst

        Log.d("testchat", userPrompt)
        val assistantResponse = repository.getChatGptResponse(emptyList(), userPrompt, mutableStateOf(""))
        Log.d("testchat", assistantResponse)

        // On construit le nouveau chat de l'assistant :
        // Il faudra peut-être revoir ton utilisation du temps et des dates ici !
        val assistantChatResponse = Chat(
            id = 0,
            text = assistantResponse,
            senderLabel = SenderLabel.CHATGPT_SENDER_LABEL,
            dateSent = LocalDateTime.now().format(dateFormatter),
            timeSent = LocalDateTime.now().format(timeFormatter),
            conversationName = ChatConfig.DEFAULT_CONVO_NAME
        )

        // On ajoute ce chat au cache :
        repository.insertChat(assistantChatResponse)
        // On émet le résultat (ici il faudra revoir ça car tu devrais avoir une manière
        // de tester le succès de l'opération plus fiable
        emit(Resource.Success(assistantChatResponse))
    }
}