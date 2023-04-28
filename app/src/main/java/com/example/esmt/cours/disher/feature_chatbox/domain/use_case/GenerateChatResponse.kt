package com.example.esmt.cours.disher.feature_chatbox.domain.use_case

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat
import com.example.esmt.cours.disher.feature_chatbox.data.repository.ChatRepositoryImpl
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenerateChatResponse @Inject constructor(
    private val repository: ChatRepositoryImpl
) {
    operator fun invoke(userPrompt: String): Flow<Resource<String>> = flow {

        // On émet le chargement
        emit(Resource.Loading())

        // Pour le moment je met des valeurs empty mais tu devra récupérer proprement
        // les last six chat et le state system
        val chatResponse = repository.getChatGptResponse(emptyList(), userPrompt, mutableStateOf(""))
        Log.d("testchat", chatResponse)
        // On émet le résultat (ici il faudra revoir ça car tu devrais avoir une manière
        // de tester le succès de l'opération plus fiable
        emit(Resource.Success(chatResponse))
    }
}