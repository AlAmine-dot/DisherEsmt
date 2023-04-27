package com.example.esmt.cours.disher.feature_meals.presentation.home.util

import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeUiEvent

// Cette classe représente le state du pop-up alert dialog
data class AlertDialogState(
    var isVisible: Boolean = true,
    val title: String = "",
    val bodyText: String = "",
    val confirmButtonText: String = "",
    val confirmToastText: String = "",
    val onConfirmEvent: HomeUiEvent = HomeUiEvent.ShowSnackbar("Default value"),
    val dismissButtonText: String = "",
    val dismissToastText: String = "",
    val onDismissEvent: HomeUiEvent = HomeUiEvent.OnHideAlertDialog,
)