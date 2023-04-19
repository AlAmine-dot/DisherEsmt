package com.example.esmt.cours.disher.feature_meals.presentation.search.category_details_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.use_case.ProvideCategoryFeatures
import com.example.esmt.cours.disher.feature_meals.domain.use_case.SearchMeal
import com.example.esmt.cours.disher.feature_meals.domain.utils.CategoryManager
import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryManager: CategoryManager,
    private val provideFeatures: ProvideCategoryFeatures
): ViewModel() {

    private val _uiState : MutableStateFlow<CategoryUiState> =  MutableStateFlow(
        CategoryUiState()
    )
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()


    private val _uiEvent = Channel<CategoryUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun getCategoryFeature(categoryId: Int){
        val category = categoryManager.getCategoryById(categoryId)
        provideFeatures(category,1, CategoryUiState.MEALS_PAGE_SIZE).onEach { result ->
            when (result){
                is Resource.Success -> {
                    val categoryFeature = result.data
                    val updatedState = _uiState.value.copy(
                        isLoading = false,
                        categoryFeature = categoryFeature
                    )
                    _uiState.value = updatedState
                    Log.d("testViewModel","inSuccess" + _uiState.value.toString() )

                }
                is Resource.Loading -> {
                    var updatedState = _uiState.value.copy(
                        isLoading = true
                    )
                    _uiState.value = updatedState
                    Log.d("testViewModel","inLoading " + _uiState.value.toString())

                }
                is Resource.Error -> {
                    val categoryFeature = result.data
                    var updatedState = _uiState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Oops, an unexpected error occured"
                    )
                    _uiState.value = updatedState
                    Log.d("testViewModel", "inError " +  _uiState.value.toString())

                }
            }
        }.launchIn(viewModelScope)

    }


//    fun onEvent(event: CategoryUiEvent){
//        when(event){
//            else -> {}
//        }
//    }


    fun sendUiEvent(event: CategoryUiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}

