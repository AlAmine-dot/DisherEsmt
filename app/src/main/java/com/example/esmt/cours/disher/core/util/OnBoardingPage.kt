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
        title = "Bienvenue",
        description = "Bienvenue dans Disher ! Avec notre application, vous pourrez accéder à des milliers de délicieuses recettes de cuisine."
    )

    object Second : OnBoardingPage(
        image = R.drawable.image2,
        title = "Découverte",
        description = "Découvrez notre application de recette de cuisine ! Avec des milliers de recettes pour tous les goûts et tous les niveaux de compétences culinaires, vous êtes sûr de trouver quelque chose qui vous convient."
    )

    object Third : OnBoardingPage(
        image = R.drawable.image3,
        title = "Apprendre",
        description = "Apprenez de nouvelles compétences culinaires avec Disher ! Nous sommes là pour vous aider à améliorer vos compétences culinaires et à découvrir de nouveaux ingrédients."
    )
}
