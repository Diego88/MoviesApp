package com.dmoyahur.moviesapp.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.dmoyahur.moviesapp.R

sealed class NavScreen(val route: String) {
    data object Movies : NavScreen("movies")
    data object Search : NavScreen("search")
    data object Detail : NavScreen("detail/{${NavArgs.MovieId.key}}/{${NavArgs.FromSearch.key}}") {
        fun createRoute(movieId: Int, previousRoute: String) =
            "detail/$movieId/${previousRoute == Search.route}"
    }
}

sealed class BottomNavScreen(
    val screen: NavScreen,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    data object Movies : BottomNavScreen(NavScreen.Movies, Icons.Outlined.Movie, R.string.app_name)
    data object Search : BottomNavScreen(NavScreen.Search, Icons.Outlined.Search, R.string.search)
}

enum class NavArgs(val key: String) {
    MovieId("movieId"),
    FromSearch("fromSearch")
}