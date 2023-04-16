package com.example.esmt.cours.disher.feature_meals.presentation.search

import com.example.esmt.cours.disher.feature_meals.domain.model.Meal

data class SearchUiState (

    val isSearching: Boolean = false,
    val searchHeadingTitle: String = "",
    val searchText: String = "",
    val searchResult: List<Meal> = emptyList(),
    val error: String = "",
){
    override fun toString(): String {
        return "SearchUiState(isSearching=$isSearching, searchHeadingTitle='$searchHeadingTitle', searchText='$searchText', searchResult=$searchResult, error='$error')"
    }
}
