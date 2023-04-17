package com.example.esmt.cours.disher.feature_meals.presentation.search.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.use_case.SearchMeal
import com.example.esmt.cours.disher.feature_meals.domain.utils.CategoryManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSearchViewModel @Inject constructor(
    private val categoryManager: CategoryManager,
    private val searchMeal: SearchMeal
): ViewModel() {

    private val _uiState : MutableStateFlow<MainSearchUiState> =  MutableStateFlow(
        MainSearchUiState()
    )
    val uiState: StateFlow<MainSearchUiState> = _uiState.asStateFlow()


    private val _uiEvent = Channel<MainSearchUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onMainSearchTextChange(text: String){
        _uiState.value = _uiState.value.copy(mainSearchText = text)
    }

    fun getMealCategories() : List<Category>{
        return categoryManager.getAllCategories()
    }


    fun onEvent(event: MainSearchUiEvent){
        when(event){
            is MainSearchUiEvent.onDoneMainSearching -> {
                if(_uiState.value.mainSearchText.isBlank() || _uiState.value.mainSearchText.isEmpty()){
                    return
                }

                event.focusManager.clearFocus()

                val query = _uiState.value.mainSearchText
                sendUiEvent(MainSearchUiEvent.RedirectToSearchScreen(query))

            }

            else -> {}
        }
    }


    fun sendUiEvent(event: MainSearchUiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}

