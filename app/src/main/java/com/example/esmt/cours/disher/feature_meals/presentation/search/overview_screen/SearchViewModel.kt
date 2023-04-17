package com.example.esmt.cours.disher.feature_meals.presentation.search.overview_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.use_case.SearchMeal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMeal: SearchMeal
): ViewModel() {

    private val _uiState : MutableStateFlow<SearchUiState> =  MutableStateFlow(
        SearchUiState()
    )
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()


    private val _uiEvent = Channel<SearchUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onSearchTextChange(text: String){
        _uiState.value = _uiState.value.copy(searchText = text)
    }




    init {
//        getFavoriteMeals()
    }


    fun onEvent(event: SearchUiEvent){
        when(event){
            is SearchUiEvent.onDoneSearching -> {
                event.focusManager?.clearFocus()

                if(_uiState.value.searchText.isBlank() || _uiState.value.searchText.isEmpty()){
                    return
                }

                _uiState.value = _uiState.value.copy(searchHeadingTitle = _uiState.value.searchText)
                val query = _uiState.value.searchText

                searchMeal(query).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            val newResult = result.data
                            _uiState.value = _uiState.value.copy(
                                searchResult = newResult.orEmpty(),
                                isSearching = false
                            )
                            Log.d("testSearchVM", _uiState.value.toString())
                        }
                        is Resource.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                    isSearching = true
                                )
                        }
                        is Resource.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isSearching = false,
                                searchResult = result.data.orEmpty(),
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }

            else -> {}
        }
    }


    fun sendUiEvent(event: SearchUiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}

