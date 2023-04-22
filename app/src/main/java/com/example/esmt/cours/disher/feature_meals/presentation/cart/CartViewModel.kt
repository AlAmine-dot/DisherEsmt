package com.example.esmt.cours.disher.feature_meals.presentation.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.model.CartItem
import com.example.esmt.cours.disher.feature_meals.domain.use_case.ProvideCartItems
import com.example.esmt.cours.disher.feature_meals.domain.use_case.RemoveMealFromCart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val provideCartItems: ProvideCartItems,
    private val removeMealFromCart: RemoveMealFromCart
): ViewModel() {

    private val _uiState : MutableStateFlow<CartUiState> =  MutableStateFlow(
        CartUiState()
    )
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<CartUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getCartItems()
    }


    private fun getCartItems(){

            provideCartItems().onEach { result ->
                when (result){
                    is Resource.Success -> {
                        val cartItems = result.data
                        val updatedState = _uiState.value.copy(
                            isLoading = false,
                            cartItemList = cartItems.orEmpty()

                        )

                        _uiState.value = updatedState
                        Log.d("testCartViewModel", _uiState.value.toString())

                    }
                    is Resource.Loading -> {
                        var updatedState = _uiState.value.copy(
                            isLoading = true
                        )
                        _uiState.value = updatedState
                        Log.d("testCartViewModel", _uiState.value.toString())

                    }
                    is Resource.Error -> {
                        var updatedState = _uiState.value.copy(
                            isLoading = false,
                            error = result.message ?: "Oops, an unexpected error occured"
                        )
                        _uiState.value = updatedState
                        Log.d("testCartViewModel", _uiState.value.toString())

                    }
                }
            }.launchIn(viewModelScope)

    }



    fun onEvent(event: CartUiEvent){
        when(event){
            is CartUiEvent.RemoveMealFromCart -> {
                event.cartItem.cartItemMeal.toggleIsIntoCart()
//
                removeMealFromCart(event.cartItem.cartItemMeal).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                val newCartItemsList = _uiState.value.cartItemList.toMutableList()
                                newCartItemsList.remove(event.cartItem)
                                _uiState.value = _uiState.value.copy(cartItemList = newCartItemsList)
                                sendUiEvent(CartUiEvent.ShowSnackbar("Removed recipe from cart successfully !"))
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


    private fun sendUiEvent(event: CartUiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
