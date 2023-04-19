package com.example.esmt.cours.disher.core.presentation.main_screen

sealed class UiEvent{
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent()
    object HideSnackbar: UiEvent()
}
