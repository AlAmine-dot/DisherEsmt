package com.example.esmt.cours.disher.core.presentation.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(): ViewModel() {

    private val _uiState : MutableStateFlow<MainUiState> =  MutableStateFlow(
        MainUiState()
    )
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun sendMainUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun toggleBottomBarVisibility(bool: Boolean){
        _uiState.value = _uiState.value.copy(isBottomBarVisible = bool)
    }
}