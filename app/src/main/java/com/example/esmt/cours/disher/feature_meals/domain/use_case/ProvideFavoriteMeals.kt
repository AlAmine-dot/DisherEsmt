package com.example.esmt.cours.disher.feature_meals.domain.use_case

import android.util.Log
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.CategoryFeature
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProvideFavoriteMeals @Inject constructor(
    private val repository: MealRepository
) {

    // C'est juste pour qu'on puisse appeler la classe comme si c'était une fonction
    operator fun invoke(): Flow<Resource<List<Meal>>> = flow {

        // On signale le chargement des données...
        val localResponse = repository.getFavoriteMeals()
        emit(Resource.Loading(null))

        if(localResponse.isEmpty()){
            emit(Resource.Error("Oops, no favorite meals found !"))
        }else{
            emit(Resource.Success(localResponse))
        }

        // Ici, à priori, il faudrait faire une requête vers l'API pour récupérer la toute dernière version
        // de chaque recette mis en favori pour mettre à jour le cache, mais pour des raisons de temps, je garde
        // ça pour plus tard, et puis de toute façon, l'API ne risque pas de changer pour le moment c'est sur.

    }

}