package com.example.esmt.cours.disher.feature_meals.domain.use_case

import android.util.Log
import com.example.esmt.cours.disher.core.common.Resource
import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddMealToFavorites @Inject constructor(
    private val repository: MealRepository
) {
    operator fun invoke(meal: Meal): Flow<Resource<Unit>> = flow {

        // On émet le chargement
        emit(Resource.Loading())

        repository.addMealToFavorites(meal)
        Log.d("argsvm", meal.strMealName.orEmpty())
        // On émet le résultat (ici il faudra revoir ça car tu devrais avoir une manière
        // de tester le succès de l'opération plus fiable
        emit(Resource.Success(Unit))
    }
}