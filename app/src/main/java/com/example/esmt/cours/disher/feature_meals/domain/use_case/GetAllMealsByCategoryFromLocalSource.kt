package com.example.esmt.cours.disher.feature_meals.domain.use_case

import android.util.Log
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllMealsByCategoryFromLocalSource @Inject constructor(
    private val repository: MealRepository
) {

    // C'est juste pour qu'on puisse appeler la classe comme si c'était une fonction
    operator fun invoke(category: Category?, page: Int, pageSize: Int): Flow<Resource<List<Meal>>> = flow {
        val startingIndex = (page - 1) * pageSize
        if (category != null) {
            Log.d("ArgsCategory",category.categoryName)
        }

        // On signale le chargement des données...
        val localResponse = repository.getAllMealsByCategoryFromLocalSource(category)
        emit(Resource.Loading(emptyList()))

        // On récupère les données en Offline First et on les émet :
        val localMeals = getTruncatedResponse(localResponse,startingIndex,pageSize)

        if(localMeals.isEmpty()){
            emit(Resource.Loading(emptyList()))
        }else{
            emit(Resource.Success(localMeals))
        }

        // On met ensuite à jour le cache, si l'utilisateur est connecté à internet :
        val remoteResponse = repository.getAllMealsByCategoryFromRemote(category)

        val remoteMeals = getTruncatedResponse(remoteResponse,startingIndex,pageSize)

        if(remoteMeals.isEmpty()){
            emit(Resource.Error("Oops, no internet connection !", localMeals))
        }else{
            // Ajout du contenu à la base de données :
            repository.addMealsToLocalSource(remoteMeals)
            // Et enfin on récupère les données les plus récentes :
            val finalResponse = repository.getAllMealsByCategoryFromLocalSource(category)
            val finalMeals = getTruncatedResponse(finalResponse,startingIndex, pageSize)
            emit(Resource.Success(finalMeals))
        }

    }

    private fun getTruncatedResponse(response: List<Meal>,startingIndex: Int, pageSize: Int): List<Meal> {
        Log.d("argsSize",response.size.toString())
        return if(startingIndex + pageSize <= response.size) {
            response.slice(startingIndex until startingIndex + pageSize)
        } else{
            emptyList()
        }
    }

}