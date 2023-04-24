package com.example.esmt.cours.disher.core.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.ui.theme.MeltyGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AwaitScreen(
    onNavigate: (route: String) -> Unit,
    startDestination: String
){
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            delay(2000)
            onNavigate(startDestination)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MeltyGreen),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(id = R.drawable.disher_branding_green),
            contentDescription = "Disher branding",
            modifier = Modifier.size(300.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPrev(){
    AwaitScreen({},"")
}