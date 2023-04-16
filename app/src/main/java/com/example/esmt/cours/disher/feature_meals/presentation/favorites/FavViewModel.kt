package com.example.esmt.cours.disher.feature_meals.presentation.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.use_case.ProvideFavoriteMeals
import com.example.esmt.cours.disher.feature_meals.domain.use_case.RemoveMealFromFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
    private val provideFavoriteMeals: ProvideFavoriteMeals,
    private val removeMealFromFavorites: RemoveMealFromFavorites
): ViewModel() {

    private val _uiState : MutableStateFlow<FavUiState> =  MutableStateFlow(
        FavUiState()
    )
    val uiState: StateFlow<FavUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<FavUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getFavoriteMeals()
    }


    private fun getFavoriteMeals(){

            provideFavoriteMeals().onEach { result ->
                when (result){
                    is Resource.Success -> {
                        val favoriteMeals = result.data
                        val updatedState = _uiState.value.copy(
                            isLoading = false,
                            favoriteMeals = favoriteMeals.orEmpty()
                        )

                        _uiState.value = updatedState
                        Log.d("testFavViewModel", _uiState.value.toString())

                    }
                    is Resource.Loading -> {
                        var updatedState = _uiState.value.copy(
                            isLoading = true
                        )
                        _uiState.value = updatedState
                        Log.d("testFavViewModel", _uiState.value.toString())

                    }
                    is Resource.Error -> {
                        val categoryFeature = result.data
                        var updatedState = _uiState.value.copy(
                            isLoading = false,
                            error = result.message ?: "Oops, an unexpected error occured"
                        )
                        _uiState.value = updatedState
                        Log.d("testFavViewModel", _uiState.value.toString())

                    }
                }
            }.launchIn(viewModelScope)

    }



    fun onEvent(event: FavUiEvent){
        when(event){
            is FavUiEvent.RemoveMealFromFavorites -> {
                event.meal.toggleIsFavorite()

                removeMealFromFavorites(event.meal).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                val newFavoriteList = _uiState.value.favoriteMeals.toMutableList()
                                newFavoriteList.remove(event.meal)
                                _uiState.value = _uiState.value.copy(favoriteMeals = newFavoriteList)
                                sendUiEvent(FavUiEvent.ShowSnackbar("Removed recipe of favorites successfully !"))
                            }
                            is Resource.Loading -> {
                                _uiState.value = _uiState.value.copy(
                                        isLoading = true
                                    )
                            }
                            is Resource.Error -> {
                                _uiState.value = _uiState.value.copy(
                                    error = result.message ?: "An unexpected error occurred"
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
                }
                else -> Unit
            }
        }


    private fun sendUiEvent(event: FavUiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
