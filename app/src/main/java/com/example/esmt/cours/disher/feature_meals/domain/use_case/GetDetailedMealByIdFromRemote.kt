//package com.example.esmt.cours.disher.feature_meals.domain.use_case
//
//import com.example.esmt.cours.disher.feature_meals.domain.model.Meal
//import com.example.esmt.cours.disher.feature_meals.domain.repository.MealRepository
//import javax.inject.Inject
//
//class GetDetailedMealByIdFromRemote @Inject constructor(
//    private val repository: MealRepository
//) {
//    // C'est juste pour qu'on puisse appeler la classe comme si c'était une fonction
//    suspend operator fun invoke(id: Int): Meal?  {
//
//        return repository.getDetailedMealByIdFromRemote(id)
//
//    }
//}