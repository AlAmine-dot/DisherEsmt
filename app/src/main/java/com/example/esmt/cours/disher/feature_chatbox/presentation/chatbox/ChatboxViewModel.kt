package com.example.esmt.cours.disher.feature_chatbox.presentation.chatbox

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.rememberCoroutineScope
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
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

    private fun onMainPromptTextChange(text: String){
        _uiState.value = _uiState.value.copy(mainPromptField = text)
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

    private fun promptChat(chatRequest: Chat,chatListState: LazyListState,scope: CoroutineScope){
        Log.d("testChatViewModel","Came here !")

        _uiState.value = _uiState.value.copy(mainPromptField = "")
        _uiState.value.addChatMessage(chatRequest)

        scope.launch{
            chatListState.animateScrollToItem(_uiState.value.getChatMessages().size)
        }

        generateChatResponse(chatRequest).onEach { result ->
            when (result){
                is Resource.Success -> {
                    val chatResponse = result.data
                    val updatedState = _uiState.value.copy(
                        isTyping = false,
                    )
                        Log.d("testChatViewModel", chatResponse.toString())

                    if (chatResponse != null) {
                        updatedState.replaceLastChatMessage(chatResponse)
                    }
                    _uiState.value = updatedState
                        scope.launch{
                            chatListState.animateScrollToItem(_uiState.value.getChatMessages().size)
                        }
                    Log.d("testChatViewModel", _uiState.value.toString())

                }
                is Resource.Loading -> {
                    val fakeResponse = result.data
                    var updatedState = _uiState.value.copy(
                        isTyping = true
                    )
                    if (fakeResponse != null) {
                        updatedState.addChatMessage(fakeResponse)
                    }
                    _uiState.value = updatedState
                    scope.launch{
                        chatListState.animateScrollToItem(_uiState.value.getChatMessages().size)
                    }
                    Log.d("testChatViewModel", _uiState.value.toString())

                }
                is Resource.Error -> {
                    val categoryFeature = result.data
                    var updatedState = _uiState.value.copy(
                        isTyping = false,
                        error = result.message ?: "Oops, an unexpected error occured"
                    )
                    _uiState.value = updatedState
                    scope.launch{
                        chatListState.animateScrollToItem(_uiState.value.getChatMessages().size)
                    }
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
                promptChat(newChat,event.chatListState, event.scope)
            }
            is ChatboxUiEvent.OnMainPromptTextChange -> {
                onMainPromptTextChange(event.newPrompt)
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
