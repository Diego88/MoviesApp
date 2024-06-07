package com.dmoyahur.moviesapp.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.dmoyahur.moviesapp.R
import com.dmoyahur.moviesapp.common.ui.icons.CustomIcons
import com.dmoyahur.moviesapp.common.ui.icons.filled.VideoSearch
import com.dmoyahur.moviesapp.feature.movies.navigation.MOVIES_ROUTE
import com.dmoyahur.moviesapp.feature.search.navigation.SEARCH_ROUTE

enum class BottomNavScreen(
    val route: String,
    val unSelectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    @StringRes val label: Int
) {
    MOVIES(
        route = MOVIES_ROUTE,
        unSelectedIcon = Icons.Outlined.Movie,
        selectedIcon = Icons.Filled.Movie,
        label = R.string.movies
    ),
    SEARCH(
        route = SEARCH_ROUTE,
        unSelectedIcon = Icons.Outlined.Search,
        selectedIcon = CustomIcons.Filled.VideoSearch,
        label = R.string.search
    )
}