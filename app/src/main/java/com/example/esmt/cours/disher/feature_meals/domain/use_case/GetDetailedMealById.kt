package com.example.esmt.cours.disher.feature_meals.domain.use_case

import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetailedMealById @Inject constructor(
    private val repository: MealRepository
) {
    // C'est juste pour qu'on puisse appeler la classe comme si c'était une fonction
    operator fun invoke(id: Int): Flow<Resource<Meal?>> = flow {

        // On signale le chargement des ressources :
        emit(Resource.Loading())

        // On récupère la recette depuis le cache :
        val localResponse = repository.getDetailedMealByIdFromLocalSource(id)

        if(localResponse == null){
            // Il n'y a rien dans le cache, on continue le chargement des ressources :
            emit(Resource.Loading())
        }else {
            // Sinon, on envoie la réponse
            emit(Resource.Success(localResponse))
        }

        // On rafraichit le cache en récupérant une version plus récente de la recette depuis l'API

        val remoteResponse = repository.getDetailedMealByIdFromRemote(id)
        // Tu pourrais remplacer ces trucs par des switch case :

        if(remoteResponse == null){
            // Si rien n'a été trouvé depuis l'API, il y'a surement un problème de connexion
            // (cette hypothèse est présomptueuse, il faudra revenir sur cette partie du code et dans les autres use case
            // utilisant la même logique

            emit(Resource.Error("Oops, no internet connection !"))
        }else if(remoteResponse == localResponse){
            // S'il n'y a aucune nouveauté, pas la peine de rafraîchir, on ne fait rien.
        }
        else {
            // Sinon, on finit la rafraîchissement du cache et on récupère la réponse finale :
            repository.addMealsToLocalSource(listOf(remoteResponse))
            val finalResponse = repository.getDetailedMealByIdFromLocalSource(id)

            emit(Resource.Success(finalResponse))
        }

    }
}