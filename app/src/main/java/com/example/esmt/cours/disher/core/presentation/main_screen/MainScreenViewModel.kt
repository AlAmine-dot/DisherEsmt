package com.example.esmt.cours.disher.core.presentation.main_screen

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.ConnectivityObserver
import com.example.esmt.cours.disher.core.common.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val connectivityObserver: NetworkConnectivityObserver
): ViewModel() {



    init{
        Log.d("obsCon", connectivityObserver.toString())
    }

    private val _uiState : MutableStateFlow<MainUiState> =  MutableStateFlow(
        MainUiState(connectivityObserver = connectivityObserver)
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