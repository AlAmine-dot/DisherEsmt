package com.example.esmt.cours.disher.feature_meals.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.use_case.ProvideCategoryFeatures
import com.example.esmt.cours.disher.feature_meals.domain.utils.CategoryManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryManager: CategoryManager,
    private val provideFeatures: ProvideCategoryFeatures
): ViewModel() {


    private val _uiState : MutableStateFlow<HomeUiState> =  MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    init {
        getMeals()
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnToggleFeedMode -> {
                _uiState.value = _uiState.value.copy(
                    feedModeOption = event.newFeedMode
                )
            }

            else -> {}
        }
    }

    private fun getMeals(){

        var categories = listOf(
            categoryManager.getCategoryByName("Miscellaneous"),
            categoryManager.getCategoryByName("Breakfast"),
            categoryManager.getCategoryByName("Beef"),
            categoryManager.getCategoryByName("Dessert"),
            categoryManager.getCategoryByName("Vegetarian"),
        )
        categories.forEach { singleCategory ->

        provideFeatures(singleCategory,1,HomeUiState.MEALS_PAGE_SIZE).onEach { result ->
                when (result){
                    is Resource.Success -> {
                        val categoryFeature = result.data
                        val updatedState = _uiState.value.copy(
                            isLoading = false
                        )
                            .apply {
                            if (categoryFeature != null && !this.getCategoryFeatures().contains(categoryFeature)) {
                                addCategoryFeature(categoryFeature)
                            }
                        }

                        _uiState.value = updatedState
                        Log.d("testViewModel", _uiState.value.toString())

                    }
                    is Resource.Loading -> {
                        var updatedState = _uiState.value.copy(
                            isLoading = true
                        )
                        _uiState.value = updatedState
                        Log.d("testViewModel", _uiState.value.toString())

                    }
                    is Resource.Error -> {
                        val categoryFeature = result.data
                        var updatedState = _uiState.value.copy(
                            isLoading = false,
                            error = result.message ?: "Oops, an unexpected error occured"
                        )
                        // Est-ce qu'on a besoin d'update le contenu en cas d'erreur ?
//                        if (categoryFeature != null) {
//                            updatedState.addCategoryFeature(categoryFeature)
//                        }
                        _uiState.value = updatedState
                        Log.d("testViewModel", _uiState.value.toString())

                    }
                }
        }.launchIn(viewModelScope)

        }

    }

}