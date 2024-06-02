package com.dmoyahur.moviesapp.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.dmoyahur.moviesapp.R
import com.dmoyahur.moviesapp.feature.movies.navigation.MOVIES_ROUTE
import com.dmoyahur.moviesapp.feature.search.navigation.SEARCH_ROUTE

enum class BottomNavScreen(
    val route: String,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    MOVIES(
        route = MOVIES_ROUTE,
        icon = Icons.Outlined.Movie,
        label = R.string.app_name
    ),
    SEARCH(
        route = SEARCH_ROUTE,
        icon = Icons.Outlined.Search,
        label = R.string.search
    )
}