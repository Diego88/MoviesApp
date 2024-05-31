package com.dmoyahur.moviesapp.navigation

import android.app.Application
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.dmoyahur.moviesapp.data.database.MoviesDatabase
import com.dmoyahur.moviesapp.data.database.MoviesRoomDataSource
import com.dmoyahur.moviesapp.data.network.MoviesNetworkDataSource
import com.dmoyahur.moviesapp.domain.data.MoviesRepository
import com.dmoyahur.moviesapp.domain.usecases.FetchMoviesUseCase
import com.dmoyahur.moviesapp.domain.usecases.FindMovieByIdUseCase
import com.dmoyahur.moviesapp.domain.usecases.GetPreviousSearchesUseCase
import com.dmoyahur.moviesapp.domain.usecases.SearchMovieUseCase
import com.dmoyahur.moviesapp.ui.detail.DetailRoute
import com.dmoyahur.moviesapp.ui.detail.DetailViewModel
import com.dmoyahur.moviesapp.ui.movies.MoviesRoute
import com.dmoyahur.moviesapp.ui.movies.MoviesViewModel
import com.dmoyahur.moviesapp.ui.search.SearchRoute
import com.dmoyahur.moviesapp.ui.search.SearchViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val navItems = listOf(BottomNavScreen.Movies, BottomNavScreen.Search)

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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = currentRoute in navItems.map { it.screen.route },
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
                BottomNavigation {
                    navItems.forEach { item ->
                        val itemColor =
                            if (currentRoute == item.screen.route) Color.Black else Color.Gray

                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null,
                                    tint = itemColor
                                )
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            label = {
                                Text(
                                    text = stringResource(id = item.label),
                                    color = itemColor
                                )
                            }
                        )
                    }
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = NavScreen.Movies.route
        ) {
            composable(NavScreen.Movies.route) {
                MoviesRoute(
                    viewModel { MoviesViewModel(FetchMoviesUseCase(moviesRepository)) },
                    onMovieClick = { movie ->
                        navController.navigate(NavScreen.Detail.createRoute(movie.id, NavScreen.Movies.route))
                    }
                )
            }
            composable(
                route = NavScreen.Detail.route,
                arguments = listOf(
                    navArgument(NavArgs.MovieId.key) { type = NavType.IntType },
                    navArgument(NavArgs.FromSearch.key) { type = NavType.BoolType })
            ) { backStackEntry ->
                val movieId =
                    requireNotNull(backStackEntry.arguments?.getInt(NavArgs.MovieId.key))
                val fromSearch =
                    requireNotNull(backStackEntry.arguments?.getBoolean(NavArgs.FromSearch.key))
                DetailRoute(
                    viewModel {
                        DetailViewModel(
                            FindMovieByIdUseCase(moviesRepository),
                            movieId,
                            fromSearch
                        )
                    },
                    onBack = { navController.popBackStack() })
            }
            composable(NavScreen.Search.route) {
                SearchRoute(
                    viewModel {
                        SearchViewModel(
                            getPreviousSearchesUseCase = GetPreviousSearchesUseCase(moviesRepository),
                            searchMovieUseCase = SearchMovieUseCase(moviesRepository)
                        )
                    },
                    onMovieClick = { movie ->
                        navController.navigate(NavScreen.Detail.createRoute(movie.id, NavScreen.Search.route))
                    }
                )
            }
        }
    }
}