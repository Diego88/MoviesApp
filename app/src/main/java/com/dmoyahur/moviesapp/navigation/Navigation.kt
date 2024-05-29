package com.dmoyahur.moviesapp.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.dmoyahur.database.MoviesDatabase
import com.dmoyahur.database.MoviesRoomDataSource
import com.dmoyahur.domain.data.MoviesRepository
import com.dmoyahur.domain.usecases.FetchMoviesUseCase
import com.dmoyahur.domain.usecases.FindMovieByIdUseCase
import com.dmoyahur.remote.MoviesNetworkDataSource
import com.dmoyahur.ui.detail.DetailRoute
import com.dmoyahur.ui.detail.DetailViewModel
import com.dmoyahur.ui.movies.MoviesRoute
import com.dmoyahur.ui.movies.MoviesViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val appContext = LocalContext.current.applicationContext as Application
    val db by lazy {
        Room.databaseBuilder(
            appContext,
            MoviesDatabase::class.java,
            "movies.db"
        ).build()
    }
    val moviesRepository = remember {
        MoviesRepository(
            remoteDataSource = MoviesNetworkDataSource(),
            localDataSource = MoviesRoomDataSource(db.moviesDao())
        )
    }

    NavHost(navController = navController, startDestination = NavScreen.Movies.route) {
        composable(NavScreen.Movies.route) {
            MoviesRoute(
                viewModel { MoviesViewModel(FetchMoviesUseCase(moviesRepository)) },
                onMovieClick = { movie ->
                    navController.navigate(NavScreen.Detail.createRoute(movie.id))
                }
            )
        }
        composable(
            route = NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.MovieId.key) { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getInt(NavArgs.MovieId.key))
            DetailRoute(
                viewModel { DetailViewModel(FindMovieByIdUseCase(moviesRepository), movieId) },
                onBack = { navController.popBackStack() })
        }
    }
}