package com.example.esmt.cours.disher.feature_meals.domain.use_case

import android.util.Log
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.model.Category
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
import com.example.esmt.cours.disher.feature_meals.presentation.home.util.CategoryFeature
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject

class ProvideRandomMealsCollection @Inject constructor(
    private val repository: MealRepository
) {

    // C'est juste pour qu'on puisse appeler la classe comme si c'était une fonction
    operator fun invoke(pageSize: Int,collectionName: String, emojis: List<String>): Flow<Resource<CategoryFeature>> = flow {

        val featureTitle = collectionName

        // On signale le chargement des données...
        val localMeals = repository.getRandomMealsFromLocalSource(pageSize)
        Log.d("testProvideForCategoryD","LR : " + localMeals.toString())
        emit(Resource.Loading(null))

        if(localMeals.isEmpty()){
            emit(Resource.Error("No meals found !", null))
        }else{
            emit(Resource.Success(CategoryFeature(featureTitle, emojis = emojis, category = null, featuredMeals = localMeals)))
        }

        // On met ensuite à jour le cache, si l'utilisateur est connecté à internet :
        // On ne peut pas encore récupérer des random meals via l'API car il faut payer un support
        // patreon. Et de toute manière, je ne pense pas qu'on aura besoin de le faire pour le moment.

        // Si le besoin devient primordial, tu pourras toujours créer un petit truc.

    }

}