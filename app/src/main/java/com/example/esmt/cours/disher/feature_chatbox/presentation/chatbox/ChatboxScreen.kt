package com.example.esmt.cours.disher.feature_chatbox.presentation.chatbox

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChatboxScreen (
    chatboxViewModel: ChatboxViewModel = hiltViewModel(),
){
    LaunchedEffect(key1 = true){
        Log.d("testChatboxScreen", chatboxViewModel.toString())
        chatboxViewModel.promptChat("J'aimerais un plat à base de pates bien consistant ce soir mais je ne veux pas trop de boulot.")
    }
}