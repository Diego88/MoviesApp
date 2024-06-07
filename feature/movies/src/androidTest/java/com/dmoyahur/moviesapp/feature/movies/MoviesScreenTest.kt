package com.dmoyahur.moviesapp.feature.movies

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.dmoyahur.moviesapp.testShared.MovieMock
import com.dmoyahur.moviesapp.common.ui.components.Screen
import com.dmoyahur.moviesapp.feature.movies.ui.MoviesScreen
import com.dmoyahur.moviesapp.feature.movies.ui.MoviesUiState
import org.junit.Rule
import org.junit.Test

class MoviesScreenTest {

    @get:Rule()
    val composeTestRule = createComposeRule()

    @Test
    fun givenAListOfPopularMovies_PopularMoviesAreShown(): Unit = with(composeTestRule) {
        val movies = MovieMock.movies
        val state = MoviesUiState(
            movies = movies,
            error = null,
            loading = false
        )

        setContent {
            Screen {
                MoviesScreen(state) {}
            }
        }

        onNodeWithText("Movie 1").assertIsDisplayed()
        onNodeWithText("Movie 2").assertIsDisplayed()
        onNodeWithText("Movie 3").assertIsDisplayed()
    }
}