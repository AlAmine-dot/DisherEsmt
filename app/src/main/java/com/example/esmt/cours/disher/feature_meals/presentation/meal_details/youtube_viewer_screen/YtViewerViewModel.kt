package com.example.esmt.cours.disher.feature_meals.presentation.meal_details.youtube_viewer_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.NetworkConnectivityObserver
import com.example.esmt.cours.disher.feature_meals.presentation.meal_details.details_screen.MealDetailsUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YtViewerViewModel @Inject constructor(
    private val connectivityObserver: NetworkConnectivityObserver
): ViewModel() {


    private val _uiState : MutableStateFlow<YtViewerUiState> =  MutableStateFlow(
        YtViewerUiState(
            connectivityObserver = connectivityObserver
        )
    )
    val uiState: StateFlow<YtViewerUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<YtViewerUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: YtViewerUiEvent) {
        when (event) {
            is YtViewerUiEvent.onLoadVideo -> {
                val videoUrl = event.videoUrl
                if(videoUrl.isBlank()){
                    _uiState.value = _uiState.value.copy(
                        error = "Oops, couldn't load video from url ! Please try again :/"
                    )
                }else{
                    _uiState.value = _uiState.value.copy(
                        mealVideoUrl = videoUrl
                    )
                }
            }

            else -> {}
        }
    }

    private fun sendUiEvent(event: YtViewerUiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}