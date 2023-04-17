package com.example.esmt.cours.disher.feature_meals.domain.use_case

import android.content.Context
import android.net.ConnectivityManager
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCategories @Inject constructor(
    private val repository: MealRepository
) {

    // C'est juste pour qu'on puisse appeler la classe comme si c'était une fonction
//    operator fun invoke(): Flow<Resource<List<Category>>> = flow {
//
//        // On signale le chargement des données...
//        val localResponse = repository.getAllCategoriesFromLocalSource()
//        emit(Resource.Loading(emptyList()))
//
//        // On récupère les données en Offline First et on les émet :
//        if(localResponse.isNotEmpty()){
//            emit(Resource.Success(localResponse))
//        }
//
//        emit(Resource.Loading())
//        // On met ensuite à jour le cache, si l'utilisateur est connecté à internet :
//        val remoteResponse = repository.getAllCategoriesFromRemote()
//        var response = emptyList<Category>()
//        if(remoteResponse.isEmpty()){
//            emit(Resource.Error("Oops, no internet connection !", localResponse))
//            response = localResponse
//        }else{
//            // Ajout du contenu à la base de données :
//            repository.addCategoriesToLocalSource(remoteResponse)
//            // Et enfin on récupère les données les plus récentes :
//            val finalResponse = repository.getAllCategoriesFromLocalSource()
//            response = finalResponse
//        }
//        emit(Resource.Success(response,"FINAL"))
//
//    }

}
