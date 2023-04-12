package com.example.esmt.cours.disher.feature_meals.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.use_case.BuildAndGetCategoryFeatures
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
//    private val getAllCategories: GetAllCategories,
    private val provideFeatures: BuildAndGetCategoryFeatures
): ViewModel() {


    private val _uiState : MutableStateFlow<HomeState> =  MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

//    private var isCategoriesRetrievedSuccessfully = false

    init {
        getMeals()
    }

//    private fun getMealCategories(): Boolean{
//        getAllCategories().onEach { result ->
//            when (result){
//                is Resource.Success -> {
//                    _uiState.value = HomeState(
//                        mealCategories = result.data ?: emptyList()
//                    )
//                    isCategoriesRetrievedSuccessfully = true
//                    Log.d("testViewModel",result.message.orEmpty())
//                    if(result.message.equals("FINAL")){
//                    // Il nous faut vraiment une méthode pour vérifier si la personne est connectée ou pas là
//                        getMeals()
//                    }
//                    Log.d("testViewModel","isTruehere")
//                }
//                is Resource.Loading -> {
//                    _uiState.value = HomeState(
//                        isLoading = true
//                    )
//                    Log.d("testViewModel","isFalsehere")
//                }
//                is Resource.Error -> {
//                    _uiState.value = HomeState(
//                        error = result.message ?: "An unexpected error occurred",
//                        mealCategories = result.data ?: emptyList()
//                    )
//                }
//            }
//        }.launchIn(viewModelScope)
//
//        return true
//    }

    private fun getMeals(){
//        if (!isCategoriesRetrievedSuccessfully || uiState.value.mealCategories.isEmpty()) {
//            // Do not fetch meals if categories are not retrieved successfully or if the list is empty
//            _uiState.value = HomeState(error = "Failed to retrieve categories")
//            return
//        }

//        var singleCategory = uiState.value.mealCategories.first()
        var categories = listOf(
            Category(0,"Miscellaneous","test","test"),
            Category(0,"Beef","test","test"),
            Category(0,"Breakfast","test","test"),
        )
        categories.forEach { singleCategory ->

        provideFeatures(singleCategory,"Trending in ${singleCategory.categoryName}",1,HomeState.MEALS_PAGE_SIZE).onEach { result ->
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