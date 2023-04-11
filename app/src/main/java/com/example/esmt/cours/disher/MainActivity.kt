package com.example.esmt.cours.disher

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeScreen
//import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeScreen
import com.example.esmt.cours.disher.ui.theme.DisherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisherTheme {
                // A surface container using the 'background' color from the theme
                        HomeScreen()
//                    Greeting("Morioh !")
            }

        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    DisherTheme {
//        Greeting("Android")
//    }
//}