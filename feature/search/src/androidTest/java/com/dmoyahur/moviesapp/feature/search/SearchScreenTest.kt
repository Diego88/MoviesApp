package com.dmoyahur.moviesapp.feature.search

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.dmoyahur.moviesapp.core.testing.MovieMock
import com.dmoyahur.moviesapp.core.ui.components.Screen
import com.dmoyahur.moviesapp.feature.search.ui.SearchScreen
import com.dmoyahur.moviesapp.feature.search.ui.SearchUiState
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule()
    val composeTestRule = createComposeRule()

    @Test
    fun givenEmptyPreviousSearches_PlaceHolderContentIsShown(): Unit = with(composeTestRule) {
        val searchUiState = SearchUiState(previousSearches = emptyList())
        setContent {
            Screen {
                SearchScreen(searchUiState, {}, {}, {})
            }
        }

        onNodeWithText("Type the movie to search").assertIsDisplayed()
    }

    @Test
    fun givenPreviousSearches_PreviousSearchesAreShown(): Unit = with(composeTestRule) {
        val movies = MovieMock.movies.take(2)
        val searchUiState = SearchUiState(previousSearches = movies)
        setContent {
            Screen {
                SearchScreen(searchUiState, {}, {}, {})
            }
        }

        onNodeWithText("Previous searches").assertIsDisplayed()
        onNodeWithText("Movie 1").assertIsDisplayed()
        onNodeWithText("Movie 2").assertIsDisplayed()
    }

    @Test
    fun givenAMovieSearchWithResults_MoviesResultAreShown(): Unit = with(composeTestRule) {
        val movies = MovieMock.movies
        val searchUiState = SearchUiState(
            query = "Movie",
            active = true,
            movies = movies
        )
        setContent {
            Screen {
                SearchScreen(searchUiState, {}, {}, {})
            }
        }

        onNodeWithText("Main results").assertIsDisplayed()
        onNodeWithText("Movie 1").assertIsDisplayed()
        onNodeWithText("Movie 2").assertIsDisplayed()
    }

    @Test
    fun givenAnEmptyMovieSearch_NoResultAreFoundIsShown(): Unit = with(composeTestRule) {
        val searchUiState = SearchUiState(
            query = "Movii",
            active = true,
            movies = emptyList()
        )
        setContent {
            Screen {
                SearchScreen(searchUiState, {}, {}, {})
            }
        }

        onNodeWithText("No results found").assertIsDisplayed()
    }

    @Test
    fun givenASearchError_ErrorScreenIsShown(): Unit = with(composeTestRule) {
        val error = "Search error"
        val searchUiState = SearchUiState(
            query = "Movie",
            active = true,
            error = Throwable(error)
        )
        setContent {
            Screen {
                SearchScreen(searchUiState, {}, {}, {})
            }
        }

        onNodeWithText(error).assertIsDisplayed()
    }
}