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

class IsMealIntoCart @Inject constructor(
    private val repository: MealRepository
) {
    operator fun invoke(meal: Meal): Flow<Resource<Boolean>> = flow {

        // On émet le chargement
        emit(Resource.Loading())

        val response = repository.isMealIntoCart(meal)
        Log.d("testargsvm", response.toString())
        emit(Resource.Success(response))
    }
}