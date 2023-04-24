package com.example.esmt.cours.disher.core.util

import androidx.annotation.DrawableRes
import com.example.esmt.cours.disher.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.image1,
        title = "Loads of ideas",
        description = "Welcome, fellow disher ! Find what you're looking for with over 76,000 recipes to satisfy any craving (and your fridge's too)."
    )

    object Second : OnBoardingPage(
        image = R.drawable.image2,
        title = "Discovery",
        description = "Here, there is something for everyone! With thousands of recipes for all tastes and skill levels, you are sure to find something that suits you."
    )

    object Third : OnBoardingPage(
        image = R.drawable.image3,
        title = "Learn",
        description = "Learn new cooking skills with Disher ! We're here to help you improve your cooking skills and discover new ingredients."
    )
}
