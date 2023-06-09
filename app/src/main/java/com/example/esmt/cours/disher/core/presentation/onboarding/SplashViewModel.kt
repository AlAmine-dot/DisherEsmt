package com.example.esmt.cours.disher.core.presentation.onboarding

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.data.local.repository.DataStoreRepository
import com.example.esmt.cours.disher.core.presentation.graphs.Graph
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Graph.AWAIT.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            repository.readOnBoardingState().collect { completed ->
                if (completed) {
                    _startDestination.value = Graph.HOME.route
                    Log.d("testSVM", "New value for sD :" + _startDestination.value)
                } else {
                    _startDestination.value = Graph.ONBOARDING.route
                    Log.d("testSVM", "New value for sD :" + _startDestination.value)
                }
            }
            _isLoading.value = false
        }
    }

}