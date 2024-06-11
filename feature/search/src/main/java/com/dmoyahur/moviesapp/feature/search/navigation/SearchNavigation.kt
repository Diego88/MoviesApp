package com.dmoyahur.moviesapp.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmoyahur.moviesapp.feature.search.ui.SearchRoute
import com.dmoyahur.moviesapp.model.MovieBo

const val SEARCH_ROUTE = "search"

fun NavGraphBuilder.searchScreen(onMovieClick: (MovieBo) -> Unit) {
    composable(SEARCH_ROUTE) {
        SearchRoute(onMovieClick = onMovieClick)
    }
}