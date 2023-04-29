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

class ClearConversation @Inject constructor(
    private val repository: ChatRepositoryImpl
) {
    operator fun invoke(): Flow<Resource<Unit>> = flow {
        // On émet le chargement
        emit(Resource.Loading())

        repository.deleteAllChats()
        emit(Resource.Success(Unit))
    }
}