package com.example.esmt.cours.disher.feature_meals.presentation.meal_details.details_screen

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.use_case.AddMealToFavorites
import com.example.esmt.cours.disher.feature_meals.domain.use_case.GetDetailedMealById
import com.example.esmt.cours.disher.feature_meals.domain.use_case.RemoveMealFromFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    private val addMealToFavorites: AddMealToFavorites,
    private val removeMealFromFavorites: RemoveMealFromFavorites,
    private val getDetailedMealById: GetDetailedMealById
): ViewModel() {


    private val _uiState : MutableStateFlow<MealDetailsUiState> =  MutableStateFlow(
        MealDetailsUiState()
    )
    val uiState: StateFlow<MealDetailsUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<MealDetailsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: MealDetailsUiEvent){
        when(event){
            is MealDetailsUiEvent.ToggleTopBar -> {
                _uiState.value = _uiState.value.copy(
                    isTopBarVisible = event.newState
                )
            }
            is MealDetailsUiEvent.ToggleMealDetailsOption -> {
                _uiState.value = _uiState.value.copy(
                    mealDetailsOption = event.newOption
                )
            }
            is MealDetailsUiEvent.ToggleMealFromFavorite -> {
                event.meal.toggleIsFavorite()

                if(event.meal.isFavorite){
                        val mealToAdd = event.meal
                        addMealToFavorites(mealToAdd).onEach { result ->
                            when (result) {
                                is Resource.Success -> {
                                        _uiState.value = _uiState.value.copy(
                                            favoriteButtonState = MealDetailsUiState.Companion.FavoriteButtonState(
                                                isToAdd = false,
                                                text = "Remove from favorites",
                                                icon = Icons.Default.Delete
                                            ),
                                        isMealToFavorites = mealToAdd.isFavorite
                                        // Je me demande ici est-ce que ça ne va pas retirer le detailedMeal du state mais on verra
                                    )
                                    sendUiEvent(MealDetailsUiEvent.ShowSnackbar("Added recipe to favorites successfully !"))
                                }
                                is Resource.Loading -> {
                                    _uiState.value = _uiState.value.copy(
                                        favoriteButtonState = MealDetailsUiState.Companion.FavoriteButtonState(
                                            isLoading = true
                                        )
                                        // Je me demande ici est-ce que ça ne va pas retirer le detailedMeal du state mais on verra
                                    )
                                }
                                is Resource.Error -> {
                                    _uiState.value = _uiState.value.copy(
                                        error = result.message ?: "An unexpected error occurred"
                                    )
                                }
                            }
                        }.launchIn(viewModelScope)
                    }else {
                        val mealToRemove = event.meal
                        removeMealFromFavorites(mealToRemove).onEach { result ->
                            when (result) {
                                is Resource.Success -> {
                                    _uiState.value = _uiState.value.copy(
                                        favoriteButtonState = MealDetailsUiState.Companion.FavoriteButtonState(
                                            isToAdd = true,
                                            text = "Add to favorites",
                                            icon = Icons.Default.Favorite
                                        ),
                                        isMealToFavorites = !mealToRemove.isFavorite
                                    )
                                    sendUiEvent(MealDetailsUiEvent.ShowSnackbar("Recipe removed from favorites."))
                                }
                                is Resource.Loading -> {
                                    _uiState.value = _uiState.value.copy(
                                        favoriteButtonState = MealDetailsUiState.Companion.FavoriteButtonState(
                                            isLoading = true
                                        )
                                        // Je me demande ici est-ce que ça ne va pas retirer le detailedMeal du state mais on verra
                                    )
                                }
                                is Resource.Error -> {
                                    _uiState.value = _uiState.value.copy(
                                        error = result.message ?: "An unexpected error occurred"
                                    )
                                    sendUiEvent(MealDetailsUiEvent.ShowSnackbar("Oops, an unexpected error occured, please retry !"))
                                }
                            }
                        }.launchIn(viewModelScope)
                    }

            }
            else -> Unit
        }
    }

    private fun sendUiEvent(event: MealDetailsUiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun getDetailedMeal(id: Int){
        Log.d("MealDetailsViewModel", id.toString())
        getDetailedMealById(id).onEach { result ->
            when (result){
                is Resource.Success -> {
                    val isFavorite = result.data.let { it?.isFavorite ?: false }
                            Log.d("testMealVM", isFavorite.toString())
                    _uiState.value = MealDetailsUiState(
                        detailedMeal = result.data,
                        quantifiedIngredients = getQuantifiedIngredients(result.data?.ingredients.orEmpty(),result.data?.measures.orEmpty()),
                        favoriteButtonState = MealDetailsUiState.Companion.FavoriteButtonState(
                            isLoading = false,
                            // Pour le moment j'initialise à false mais après tu devras avoir un attribut dans tes objets pour savoir si
                            // le repas est en favoris ou pas
                            isToAdd = isFavorite,
                            text = if(!isFavorite){"Add to favorites"}else{"Remove from favorites"},
                            icon = if(!isFavorite){Icons.Default.Favorite}else{Icons.Default.Delete},
                        )
                    )
                    Log.d("testMealVM", _uiState.value.toString())

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

    fun getQuantifiedIngredients(ingredients: List<String>, measures: List<String>): List<MealDetailsUiState.Companion.QuantifiedIngredient> {
        val combined = ingredients.zip(measures)
        return combined.mapNotNull { pair ->
            val name = pair.first.trim()
            val quantity = pair.second.trim()
            if (name.isNotEmpty() && quantity.isNotEmpty()) {
                MealDetailsUiState.Companion.QuantifiedIngredient(
                    name,
                    "https://www.themealdb.com/images/ingredients/${name}-small.png",
                    quantity
                )
            } else {
                null
            }
        }
    }

}