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

class ProvideCategoryFeatures @Inject constructor(
    private val repository: MealRepository
) {


    // C'est juste pour qu'on puisse appeler la classe comme si c'était une fonction
    operator fun invoke(category: Category?, page: Int, pageSize: Int): Flow<Resource<CategoryFeature>> = flow {
        val startingIndex = (page - 1) * pageSize
        if (category != null) {
            Log.d("ArgsCategory",category.categoryName)
        }
        // Les titres de catégories que nous retourne l'API sont un peu impersonnels, on refactor
        // le tout ici en vitesse pour un accueil plus chaleureux
        val featureTitle = when(category?.categoryName){
            "Miscellaneous" -> {
                "Our trending recipes"
            }
            "Breakfast" -> {
                "For the breakfast"
            }
            "Dessert" -> {
                "To complete your meals"
            }
            "Beef" -> {
                "Eat meat, eat spicy"
            }
            "Vegetarian" -> {
                "For the vegetarians"
            }
            else -> {
                "Trending in ${category?.categoryName?.lowercase(Locale.ROOT)}"
            }
        }

        val featureEmojis = when(category?.categoryName){
            "Miscellaneous" -> {
                "\uD83D\uDD25"
            }
            "Breakfast" -> {
                "\uD83C\uDF73"
            }
            "Dessert" -> {
                "\uD83C\uDF70"
            }
            "Beef" -> {
                "\uD83E\uDD69"
            }
            "Vegetarian" -> {
                "\uD83E\uDD57"
            }
            else -> {
                ""
            }
        }


        // On signale le chargement des données...
        val localResponse = repository.getAllMealsByCategoryFromLocalSource(category)
        Log.d("testProvideForCategoryD","LR : " + localResponse.toString())
        emit(Resource.Loading(null))

        // On récupère les données en Offline First et on les émet :
        val localMeals = getTruncatedResponse(localResponse,startingIndex,pageSize)
        Log.d("testProvideForCategoryD","TR : " + localMeals.toString())

        if(localMeals.isEmpty()){
            emit(Resource.Loading(null))
        }else{
            emit(Resource.Success(CategoryFeature(featureTitle,category,localMeals,listOf(featureEmojis))))
            if (category != null) {
                Log.d("buildAndGet",category.categoryName)
            }
        }

        // On met ensuite à jour le cache, si l'utilisateur est connecté à internet :
        val remoteResponse = repository.getAllMealsByCategoryFromRemote(category)

        Log.d("testProvideForCategoryD","RR : " + remoteResponse.toString())
        val remoteMeals = getTruncatedResponse(remoteResponse,startingIndex,pageSize)
        Log.d("testProvideForCategoryD","TR : " + remoteMeals.toString())

        Log.d("testProvideForCategoryD",remoteMeals.toString())
        if(remoteMeals.isEmpty()){
            emit(Resource.Error("Oops, no internet connection !", null))
        }else{
            // Ajout du contenu à la base de données :
            repository.addMealsToLocalSource(remoteMeals)
            // Et enfin on récupère les données les plus récentes :
            val finalResponse = repository.getAllMealsByCategoryFromLocalSource(category)
            val finalMeals = getTruncatedResponse(finalResponse,startingIndex, pageSize)
            emit(Resource.Success(CategoryFeature(featureTitle,category,finalMeals, listOf(featureEmojis))))
        }

    }

    private fun getTruncatedResponse(response: List<Meal>,startingIndex: Int, pageSize: Int): List<Meal> {
        Log.d("argsSize",response.size.toString())

        return if(pageSize == -1){
            response
        }
        else if(startingIndex + pageSize <= response.size) {
            response.slice(startingIndex until startingIndex + pageSize)
        } else{
            emptyList()
        }
    }

}