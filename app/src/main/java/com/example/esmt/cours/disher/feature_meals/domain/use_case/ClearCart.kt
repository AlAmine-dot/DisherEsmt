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

class ClearCart @Inject constructor(
    private val repository: MealRepository
) {
    operator fun invoke(): Flow<Resource<Unit>> = flow {

        // On émet le chargement
        emit(Resource.Loading())

        repository.clearCart()
        // On émet le résultat (ici il faudra revoir ça car tu devrais avoir une manière
        // de tester le succès de l'opération plus fiable
        emit(Resource.Success(Unit))
    }
}