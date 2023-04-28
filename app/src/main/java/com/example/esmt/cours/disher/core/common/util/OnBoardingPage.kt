package com.example.esmt.cours.disher.core.common.util

import androidx.annotation.DrawableRes
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.ui.theme.DarkTurquoise
import com.example.esmt.cours.disher.ui.theme.MeltyGreen

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: AnnotatedString
) {
    object First : OnBoardingPage(
        image = R.drawable.image1,
        title = "Loads of ideas",
        description = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = DarkTurquoise,
                )
            ) {
                append("Welcome, fellow disher ! Find what you're looking ")
            }
            withStyle(
                style = SpanStyle(
                    color = MeltyGreen,
                )
            ) {
                append("for with over 76,000 ")
            }
            withStyle(
                style = SpanStyle(
                    color = DarkTurquoise,
                )
            ) {
                append("recipes to satisfy any craving (and your fridge's too).")
            }
        }
    )

    object Second : OnBoardingPage(
        image = R.drawable.image2,
        title = "Discovery",
        description = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = DarkTurquoise,
                )
            ) {
                append("Here, there is something for everyone! With thousands of recipes for all tastes and skill levels, ")
            }
            withStyle(
                style = SpanStyle(
                    color = MeltyGreen,
                )
            ) {
                append("you are sure to find something that suits you.")
            }
        }
    )

    object Third : OnBoardingPage(
        image = R.drawable.image3,
        title = "Learn",
        description = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = DarkTurquoise,
                )
            ) {
                append("Learn new cooking skills with Disher ! We're here to help you improve your ")
            }
            withStyle(
                style = SpanStyle(
                    color = MeltyGreen,
                )
            ) {
                append("cooking skills ")
            }
            withStyle(
                style = SpanStyle(
                    color = DarkTurquoise,
                )
            ) {
                append("and discover new ingredients.")
            }
        }

    )
}
