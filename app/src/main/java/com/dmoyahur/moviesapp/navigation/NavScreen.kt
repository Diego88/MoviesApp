package com.dmoyahur.moviesapp.navigation

sealed class NavScreen(val route: String) {
    data object Movies : NavScreen("movies")
    data object Detail : NavScreen("detail/{${NavArgs.MovieId.key}}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
}

enum class NavArgs(val key: String) {
    MovieId("movieId")
}