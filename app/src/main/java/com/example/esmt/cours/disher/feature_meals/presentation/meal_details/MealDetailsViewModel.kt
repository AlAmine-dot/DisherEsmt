package com.example.esmt.cours.disher.feature_meals.presentation.meal_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.domain.use_case.BuildAndGetCategoryFeatures
import com.example.esmt.cours.disher.feature_meals.domain.use_case.GetDetailedMealById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    private val getDetailedMealById: GetDetailedMealById
): ViewModel() {


    private val _uiState : MutableStateFlow<MealDetailsUiState> =  MutableStateFlow(MealDetailsUiState())
    val uiState: StateFlow<MealDetailsUiState> = _uiState.asStateFlow()

    fun getDetailedMeal(id: Int){
        Log.d("MealDetailsViewModel", id.toString())
        getDetailedMealById(id).onEach { result ->
            when (result){
                is Resource.Success -> {
                    _uiState.value = MealDetailsUiState(detailedMeal = result.data)
                }
                is Resource.Loading -> {
                    _uiState.value = MealDetailsUiState(isLoading = true)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)

    }

}