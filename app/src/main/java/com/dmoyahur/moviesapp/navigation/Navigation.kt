package com.dmoyahur.moviesapp.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dmoyahur.moviesapp.feature.detail.ui.DetailRoute
import com.dmoyahur.moviesapp.feature.movies.ui.MoviesRoute
import com.dmoyahur.moviesapp.feature.search.ui.SearchRoute

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val navItems = listOf(BottomNavScreen.Movies, BottomNavScreen.Search)

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
                    onMovieClick = { movie ->
                        navController.navigate(
                            NavScreen.Detail.createRoute(movie.id, NavScreen.Movies.route)
                        )
                    }
                )
            }
            composable(
                route = NavScreen.Detail.route,
                arguments = listOf(
                    navArgument(NavArgs.MovieId.key) { type = NavType.IntType },
                    navArgument(NavArgs.FromSearch.key) { type = NavType.BoolType })
            ) {
                DetailRoute(onBack = { navController.popBackStack() })
            }
            composable(NavScreen.Search.route) {
                SearchRoute(
                    onMovieClick = { movie ->
                        navController.navigate(
                            NavScreen.Detail.createRoute(movie.id, NavScreen.Search.route)
                        )
                    }
                )
            }
        }
    }
}