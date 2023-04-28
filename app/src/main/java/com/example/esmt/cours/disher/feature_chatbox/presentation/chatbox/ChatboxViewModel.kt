package com.example.esmt.cours.disher.feature_chatbox.presentation.chatbox

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_chatbox.domain.use_case.GenerateChatResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatboxViewModel @Inject constructor(
    private val generateChatResponse: GenerateChatResponse
): ViewModel() {

    private val _uiState : MutableStateFlow<ChatboxUiState> =  MutableStateFlow(
        ChatboxUiState()
    )
    val uiState: StateFlow<ChatboxUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ChatboxUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {

    }

    fun promptChat(prompt: String){
        Log.d("testChatViewModel","Came here !")
        generateChatResponse(prompt).onEach { result ->
            when (result){
                is Resource.Success -> {
                    val chatResponse = result.data
                    val updatedState = _uiState.value.copy(
                        isLoading = false,
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
                    val categoryFeature = result.data
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


    fun onEvent(event: ChatboxUiEvent){

    }


    private fun sendUiEvent(event: ChatboxUiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
