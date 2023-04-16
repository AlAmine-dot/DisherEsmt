package com.example.esmt.cours.disher.feature_meals.domain.use_case

import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMeal @Inject constructor(
    private val repository: MealRepository
) {
    // C'est juste pour qu'on puisse appeler la classe comme si c'était une fonction
    operator fun invoke(search: String): Flow<Resource<List<Meal>>> = flow {

        // On signale le chargement des données
        emit(Resource.Loading(null))

        // On récupère la réponse locale :
        val localResponse = repository.searchMealFromLocalSource(search)

//        if(localResponse.isEmpty()){
//            // On continue le chargement des données
//        }else{
//            // On émet la réponse locale
//            emit(Resource.Success(localResponse))
//        }
//            emit(Resource.Loading(null))

        // On récupère la réponse du remote :
        val remoteResponse = repository.searchMealFromRemote(search)

        if(remoteResponse == localResponse){
            emit(Resource.Success(localResponse))
        }else if(remoteResponse.isEmpty()){
            // Si la réponse du remote est vide ou identique au cache, on le signale :
            // Ici faudra revoir la logique car si la réponse est vide, ça ne veut pas dire que l'utilisateur n'est pas connecté
            emit(Resource.Error("Oops, no internet connection !", localResponse))
        }else{
            // Sinon, on met à jour le cache
            repository.addMealsToLocalSource(remoteResponse)
            // Et on récupère la réponse finale qu'on émet
            val finalResponse = repository.searchMealFromLocalSource(search)
            emit(Resource.Success(finalResponse))
        }

    }
}