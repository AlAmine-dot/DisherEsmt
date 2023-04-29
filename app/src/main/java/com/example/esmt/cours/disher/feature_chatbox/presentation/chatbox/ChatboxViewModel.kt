package com.example.esmt.cours.disher.feature_chatbox.presentation.chatbox

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat
import com.example.esmt.cours.disher.feature_chatbox.domain.use_case.GenerateChatResponse
import com.example.esmt.cours.disher.feature_chatbox.domain.use_case.GetAllChats
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.ChatConfig
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.SenderLabel
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.dateFormatter
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.timeFormatter
import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatboxViewModel @Inject constructor(
    private val generateChatResponse: GenerateChatResponse,
    private val getAllChats: GetAllChats
): ViewModel() {

    private val _uiState : MutableStateFlow<ChatboxUiState> =  MutableStateFlow(
        ChatboxUiState()
    )
    val uiState: StateFlow<ChatboxUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ChatboxUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        initChatbox()
    }

    private fun initChatbox(){

        getAllChats().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val chatResponse = result.data
                    val updatedState = _uiState.value.copy(
                        isLoading = false,
                        chatMessages = chatResponse.orEmpty()
                    )
                    Log.d("testChatViewModel", chatResponse.toString())

                    _uiState.value = updatedState
                    Log.d("testChatViewModel", _uiState.value.toString())
                }

                is Resource.Loading -> {
                    var updatedState = _uiState.value.copy(
                        isLoading = true
                    )
                    _uiState.value = updatedState
                    Log.d("testChatViewModel", _uiState.value.toString())

                }

                is Resource.Error -> {
                    var updatedState = _uiState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Oops, an unexpected error occured"
                    )
                    _uiState.value = updatedState
                    Log.d("testChatViewModel", _uiState.value.toString())

                }
            }
        }.launchIn(viewModelScope)
    }

    private fun promptChat(chatRequest: Chat){
        Log.d("testChatViewModel","Came here !")

        _uiState.value.addChatMessage(chatRequest)

        generateChatResponse(chatRequest).onEach { result ->
            when (result){
                is Resource.Success -> {
                    val chatResponse = result.data
                    val updatedState = _uiState.value.copy(
                        isTyping = false,
                    )
                        Log.d("testChatViewModel", chatResponse.toString())

                    if (chatResponse != null) {
                        updatedState.addChatMessage(chatResponse)
                    }
                    _uiState.value = updatedState
                    Log.d("testChatViewModel", _uiState.value.toString())

                }
                is Resource.Loading -> {
                    var updatedState = _uiState.value.copy(
                        isTyping = true
                    )
                    _uiState.value = updatedState
                    Log.d("testChatViewModel", _uiState.value.toString())

                }
                is Resource.Error -> {
                    val categoryFeature = result.data
                    var updatedState = _uiState.value.copy(
                        isTyping = false,
                        error = result.message ?: "Oops, an unexpected error occured"
                    )
                    _uiState.value = updatedState
                    Log.d("testChatViewModel", _uiState.value.toString())

                }
            }

        }.launchIn(viewModelScope)
    }


    fun onEvent(event: ChatboxUiEvent){
        when (event) {
            is ChatboxUiEvent.PromptChatAssistant -> {
                val newChat = Chat(
                    id = 0,
                    text = event.prompt,
                    senderLabel = SenderLabel.HUMAN_SENDER_LABEL,
                    dateSent = LocalDateTime.now().format(dateFormatter),
                    timeSent = LocalDateTime.now().format(timeFormatter),
                    conversationName = ChatConfig.DEFAULT_CONVO_NAME
                )
                Log.d("testChatViewModel", "Prompted :" + newChat)
                promptChat(newChat)
            }
            else -> {}
            }
    }


    private fun sendUiEvent(event: ChatboxUiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
