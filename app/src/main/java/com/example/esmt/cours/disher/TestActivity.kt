//package com.example.esmt.cours.disher
//
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Surface
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.lifecycle.lifecycleScope
//import com.example.esmt.cours.disher.core.data.local.MealsDatabase
//import com.example.esmt.cours.disher.feature_meals.data.remote.api.TheMealApiImpl
//import com.example.esmt.cours.disher.feature_meals.data.repository.MealRepositoryImpl
//import com.example.esmt.cours.disher.feature_meals.data.service.MealService
//import com.example.esmt.cours.disher.feature_meals.domain.use_case.*
//import com.example.esmt.cours.disher.ui.theme.DisherTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            DisherTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//
//                    // Consts for dummy tests
//                    val theMealApi = TheMealApiImpl()
//                    val mealsDao = MealsDatabase.getInstance(this).mealsDao()
//                    val favoritesDao = MealsDatabase.getInstance(this).favoriteMealsDao()
//                    val mealService = MealService(theMealApi, mealsDao, favoritesDao)
//                    val repository = MealRepositoryImpl(mealService)
//                    val useCaseCategories = GetAllCategories(repository)
//                    val useCaseMealsByCategoryFromRemote = GetAllMealsByCategoryFromRemote(repository)
//                    val useCaseMealsByCategoryFromLocalSource = GetAllMealsByCategoryFromLocalSource(repository)
//                    val useCaseDetailedMealFromRemote = GetDetailedMealByIdFromRemote(repository)
//                    val useCaseDetailedMealFromLS = GetDetailedMealByIdFromLocalSource(repository)
//                    val useCaseSearchFromRemote = SearchMealFromRemote(repository)
//                    val useCaseSearchFromLS = SearchMealFromLocalSource(repository)
//                    val useCaseAddMeals = AddMealsToLocalSource(repository)
//
//
//                    Column(
//                        Modifier.fillMaxSize()
//                    ) {
//
//
//
//                    // Dummy test fetch categories (FROM REMOTE ONLY) :
//                    lifecycleScope.launchWhenStarted{
//
//                        val categories = useCaseCategories()
//                        categories.map {
//                            Log.d("FetchCategoriesTest", it.toString())
//                        }
//                    }
//
//                    // Dummy test for adding meals to database :
//                    lifecycleScope.launchWhenStarted{
//
//                        val category = useCaseCategories().takeIf{it.isNotEmpty()}?.let{
//                            it.get(1)
//                        }
//                        val meals = useCaseMealsByCategoryFromRemote(category)
//
//                        meals.map {
//                            Log.d("FetchMealsByCategory1FromRemoteTestFORDATABASE","${it.strCategory} - " + it.toString())
//                        }
//
//                        useCaseAddMeals(meals)
//
//                    }
//
//                    // Dummy test fetch meals by category AND Search :
//
//                        // FROM REMOTE :
//
//                        lifecycleScope.launchWhenStarted{
//
//                            val category = useCaseCategories().takeIf{it.isNotEmpty()}?.let{
//                                it.get(1)
//                            }
//                            val meals = useCaseMealsByCategoryFromRemote(category)
//
//                            meals.map {
//                                Log.d("FetchMealsByCategory1FromRemoteTest","${it.strCategory} - " + it.toString())
//                            }
//
//                            val searched = useCaseSearchFromRemote("Beef")
//
//                            searched.map{
//                                Log.d("Meals found by query 'Beef' ","${it.id}, ${it.strMealName}, ${it.ingredients}, ${it.strInstructions}")
//                            }
//
//                        }
//
//                        // FROM LOCALSOURCE :
//
//                        Button(onClick = {
//                            lifecycleScope.launchWhenStarted{
//                                val category = useCaseCategories().takeIf{it.isNotEmpty()}?.let{
//                                    it.get(1)
//                                }
//                                val meals = useCaseMealsByCategoryFromLocalSource(category)
//
//                                meals.map {
//                                    Log.d("FetchMealsByCategory1FromLSTest","${it.strCategory} - " + it.toString())
//                                }
//
//                            }
//
//                        }) {
//                            Text("Test fetch meals by category from Ls")
//                        }
//
//                        Button(onClick = {
//                            lifecycleScope.launchWhenStarted{
//                                val searched = useCaseSearchFromLS("Beef")
//
//                                searched.map{
//                                    Log.d("Meals found by query 'Beef' FROM LS ","${it.id}, ${it.strMealName}, ${it.ingredients}, ${it.strInstructions}")
//                                }
//
//                            }
//
//                        }) {
//                            Text("Test search from LS")
//                        }
//
//
//
//                    // Dummy test get detailed meal by id:
//
//                        // FROM REMOTE :
//
//                        lifecycleScope.launchWhenStarted{
//
//                            val meal = useCaseDetailedMealFromRemote(52771)
//                            Log.d("Meal by id 52771 (Spicy Arrabiata Penne)", meal.toString())
//
//                        }
//
//                        // FROM LOCALSOURCE :
//
//
//                            Button(onClick = {
//                                lifecycleScope.launchWhenStarted{
//                                    val meal = useCaseDetailedMealFromLS(52765)
//                                    Log.d("Meal by id 52765 (Chicken Enchilada Casserole) FROM LS", meal.toString())
//                                }
//
//                            }) {
//                                    Text("Test fetch by ID from LS")
//                            }
//
//                    Greeting("Ohayo disher : What to make for dinner tonight?? Bruschetta Style Pork & Pasta takes roughly <b>35 minutes</b> from beginning to end. This recipe serves 5 and costs \$1.96 per serving. This main course has <b>591 calories</b>, <b>45g of protein</b>, and <b>13g of fat</b> per serving. If you have bow tie pasta, parmigiano reggiano, recipe makers chicken bruschetta pasta, and a few other ingredients on hand, you can make it. 163 people have made this recipe and would make it again. It is brought to you by Pink When. Plenty of people really liked this Mediterranean dish. With a spoonacular <b>score of 90%</b>, this dish is outstanding. Similar recipes are <a href=\\\"https://spoonacular.com/recipes/dinner-tonight-grilled-romesco-style-pork-209128\\\">Dinner Tonight: Grilled Romesco-Style Pork</a>, <a href=\\\"https://spoonacular.com/recipes/dinner-tonight-chickpea-bruschetta-31868\\\">Dinner Tonight: Chickpea Bruschetta</a>, and <a href=\\\"https://spoonacular.com/recipes/dinner-tonight-shrimp-bruschetta-from-da-zaccaria-209251\\\">Dinner Tonight: Shrimp Bruschetta from 'da Zaccaria</a>")
//                    }
//
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    DisherTheme {
//        Greeting("Android")
//    }
//}