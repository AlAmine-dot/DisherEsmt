package com.example.esmt.cours.disher.feature_meals.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.core.presentation.graphs.BottomBarScreen
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.domain.use_case.AddMealToCart
import com.example.esmt.cours.disher.feature_meals.domain.use_case.ClearCart
import com.example.esmt.cours.disher.feature_meals.domain.use_case.IsMealIntoCart
import com.example.esmt.cours.disher.feature_meals.domain.use_case.ProvideCartItems
import com.example.esmt.cours.disher.feature_meals.domain.use_case.ProvideCategoryFeatures
import com.example.esmt.cours.disher.feature_meals.domain.use_case.ProvideRandomMealsCollection
import com.example.esmt.cours.disher.feature_meals.domain.utils.CategoryManager
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.AlertDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryManager: CategoryManager,
    private val provideFeatures: ProvideCategoryFeatures,
    private val provideCartItems: ProvideCartItems,
    private val addMealToCart: AddMealToCart,
    private val isMealIntoCart: IsMealIntoCart,
    private val provideRandomMealsCollection: ProvideRandomMealsCollection,
    private val onClearCart: ClearCart
    ): ViewModel() {


    private val _uiState : MutableStateFlow<HomeUiState> =  MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        getSwiperContent()
        getMeals()

    }


    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.GenerateRandomMenu -> {
                generateRandomMenus(event.n)
            }
            is HomeUiEvent.OnToggleFeedMode -> {
                getCartItems()
                _uiState.value = _uiState.value.copy(
                    feedModeOption = event.newFeedMode
                )
            }
            is HomeUiEvent.RefreshCart -> {
                getCartItems()
            }
            is HomeUiEvent.AddMealToCart -> {
                addMealCart(event.meal)
            }
            is HomeUiEvent.OnHideAlertDialog -> {
                _uiState.value = _uiState.value.copy(
                    alertDialogState = AlertDialogState(
                        isVisible = false
                    )
                )
            }
            is HomeUiEvent.OnDiscardCart -> {
                clearCart()
            }
            is HomeUiEvent.OnShowAlertDialog -> {
                _uiState.value = _uiState.value.copy(
                    alertDialogState = event.newAlertDialog
                )
            }
            else -> {}
        }
    }

    private fun clearCart(){
        onClearCart().onEach {result ->
            when (result){
                is Resource.Success -> {
                    sendUiEvent(HomeUiEvent.ShowSnackbar("Cart succesfully cleared"))
                    getCartItems()

                }
                is Resource.Loading -> {
                    var updatedState = _uiState.value.copy(
                        isLoading = true
                    )
                    _uiState.value = updatedState
                    Log.d("testViewModel", _uiState.value.toString())

                }
                is Resource.Error -> {
                    var updatedState = _uiState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Oops, an unexpected error occured"
                    )
                    _uiState.value = updatedState
                    sendUiEvent(HomeUiEvent.ShowSnackbar(result.message ?: "Oops, an unexpected error occured"))
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun generateRandomMenus(nbOfMeals: Int){
        provideRandomMealsCollection(nbOfMeals,"Random menu",
            emptyList()
        ).onEach { result ->
            when (result){
                is Resource.Success -> {
                    val categoryFeature = result.data
                    categoryFeature?.featuredMeals?.forEach {meal ->
                        addMealToCart(meal).launchIn(viewModelScope)
                    }
                    sendUiEvent(HomeUiEvent.Navigate(BottomBarScreen.Cart.route))
                }
                is Resource.Loading -> {
                    var updatedState = _uiState.value.copy(
                        isLoading = true
                    )
                    _uiState.value = updatedState
                    Log.d("testViewModel", _uiState.value.toString())

                }
                is Resource.Error -> {
                    var updatedState = _uiState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Oops, an unexpected error occured"
                    )
                    _uiState.value = updatedState
                    sendUiEvent(HomeUiEvent.ShowSnackbar(result.message ?: "Oops, an unexpected error occured"))
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun addMealCart(meal: Meal){
            Log.d("testVM","super there :/ $isMealIntoCart")
        isMealIntoCart(meal)
            .onEach { result ->
            when (result) {

                        is Resource.Success -> {
                    val isMealIntoCart = result.data ?: false

                    if(!isMealIntoCart){
                        meal.toggleIsIntoCart()
                        val mealToAdd = meal
                        addMealToCart(mealToAdd).onEach { result ->
                            when (result) {
                                is Resource.Success -> {
//                                )
                                    sendUiEvent(HomeUiEvent.ShowSnackbar("Added recipe to cart successfully !"))
                                    getCartItems()
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
                    }else {
                        sendUiEvent(HomeUiEvent.ShowSnackbar("This recipe is already into your cart."))
                    }
                }
                else -> {
                    _uiState.value = _uiState.value.copy(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)

    }
    private fun getCartItems(){

        provideCartItems().onEach { result ->
            when (result){
                is Resource.Success -> {
                    val cartItems = result.data
                    val updatedState = _uiState.value.copy(
                        isLoading = false,
                        userCart = cartItems.orEmpty()

                    )

                    _uiState.value = updatedState
                    Log.d("testHomeViewModelCart", _uiState.value.userCart.toString())

                }
                is Resource.Loading -> {
                    var updatedState = _uiState.value.copy(
                        isLoading = true
                    )
                    _uiState.value = updatedState
                    Log.d("testHomeViewModelCart", _uiState.value.toString())

                }
                is Resource.Error -> {
                    val cartItems = result.data
                    var updatedState = _uiState.value.copy(
                        isLoading = false,
                        userCart = cartItems.orEmpty(),
                        error = result.message ?: "Oops, an unexpected error occured"
                    )
                    _uiState.value = updatedState
                    Log.d("testHomeViewModelCart", _uiState.value.toString())

                }
            }
        }.launchIn(viewModelScope)

    }

    private fun getSwiperContent(){
        provideFeatures(
            categoryManager.getCategoryByName("Side"),
            1,
            HomeUiState.MEALS_PAGE_SIZE
        )
            .onEach { result ->
                when (result){
                    is Resource.Success -> {
                        val categoryFeature = result.data
                        val updatedState = _uiState.value.copy(
                            isLoading = false,
                            swiperContent = categoryFeature
                        )

                        _uiState.value = updatedState
                        Log.d("testSwiperContent", _uiState.value.toString())

                    }
                    is Resource.Loading -> {
                        var updatedState = _uiState.value.copy(
                            isLoading = true
                        )
                        _uiState.value = updatedState
                        Log.d("testViewModel", _uiState.value.toString())

                    }
                    is Resource.Error -> {
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
    private fun getMeals(){

        var categories = listOf(
            categoryManager.getCategoryByName("Miscellaneous"),
            categoryManager.getCategoryByName("Breakfast"),
            categoryManager.getCategoryByName("Beef"),
            categoryManager.getCategoryByName("Dessert"),
            categoryManager.getCategoryByName("Vegetarian"),
        )
        categories.forEach { singleCategory ->

                provideFeatures(singleCategory,1,HomeUiState.MEALS_PAGE_SIZE)
            .onEach { result ->
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

        provideRandomMealsCollection(HomeUiState.MEALS_PAGE_SIZE,"Just 4 U",
            listOf("\uD83D\uDE0C")
        ).onEach { result ->
            when (result){
                is Resource.Success -> {
                    val categoryFeature = result.data
                    val updatedState = _uiState.value.copy(
                        isLoading = false,
                        forYouMeals = categoryFeature
                    )

                    _uiState.value = updatedState
                    Log.d("testSwiperContent", _uiState.value.toString())

                }
                is Resource.Loading -> {
                    var updatedState = _uiState.value.copy(
                        isLoading = true
                    )
                    _uiState.value = updatedState
                    Log.d("testViewModel", _uiState.value.toString())

                }
                is Resource.Error -> {
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

    private fun sendUiEvent(event: HomeUiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


}