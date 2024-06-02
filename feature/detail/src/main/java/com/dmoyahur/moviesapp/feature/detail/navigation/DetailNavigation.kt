package com.dmoyahur.moviesapp.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dmoyahur.moviesapp.feature.detail.ui.DetailRoute

enum class DetailNavArgs(val key: String) {
    MovieId("movieId"),
    FromSearch("fromSearch")
}

internal const val DETAIL_ROUTE_BASE = "detail/"
val DETAIL_ROUTE =
    "$DETAIL_ROUTE_BASE/{${DetailNavArgs.MovieId.key}}/{${DetailNavArgs.FromSearch.key}}"

fun NavController.navigateToDetail(
    movieId: Int,
    fromSearch: Boolean,
    navOptions: NavOptions? = null
) = navigate(route = "$DETAIL_ROUTE_BASE/$movieId/${fromSearch}", navOptions = navOptions)

fun NavGraphBuilder.detailScreen(onBack: () -> Unit) {
    composable(
        route = DETAIL_ROUTE,
        arguments = listOf(
            navArgument(DetailNavArgs.MovieId.key) { type = NavType.IntType },
            navArgument(DetailNavArgs.FromSearch.key) { type = NavType.BoolType })
    ) {
        DetailRoute(onBack = onBack)
    }
}