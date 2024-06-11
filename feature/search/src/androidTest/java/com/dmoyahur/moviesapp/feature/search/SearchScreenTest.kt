package com.dmoyahur.moviesapp.feature.search

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.dmoyahur.moviesapp.common.ui.components.Screen
import com.dmoyahur.moviesapp.feature.search.ui.PreviousSearchesUiState
import com.dmoyahur.moviesapp.feature.search.ui.SearchResultUiState
import com.dmoyahur.moviesapp.feature.search.ui.SearchScreen
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import com.dmoyahur.moviesapp.testShared.utils.onNodeWithText
import org.junit.Rule
import org.junit.Test
import com.dmoyahur.moviesapp.common.R as commonRes

class SearchScreenTest {

    @get:Rule()
    val composeTestRule = createComposeRule()

    @Test
    fun givenEmptyPreviousSearches_PlaceHolderContentIsShown(): Unit = with(composeTestRule) {
        val previousSearchesUiState =
            PreviousSearchesUiState.Success(previousSearches = emptyList())
        val searchesUiState = SearchResultUiState.Success(emptyList())
        setContent {
            Screen {
                SearchScreen(
                    previousSearchesUiState,
                    searchesUiState,
                    "",
                    false,
                    {},
                    {},
                    {},
                    {}
                )
            }
        }

        onNodeWithText(R.string.search_content_placeholder).assertIsDisplayed()
    }

    @Test
    fun givenPreviousSearches_PreviousSearchesAreShown(): Unit = with(composeTestRule) {
        val movies = MovieMock.movies
        val previousSearchesUiState = PreviousSearchesUiState.Success(movies)
        val searchesUiState = SearchResultUiState.Success(emptyList())
        setContent {
            Screen {
                SearchScreen(
                    previousSearchesUiState,
                    searchesUiState,
                    "",
                    false,
                    {},
                    {},
                    {},
                    {}
                )
            }
        }

        onNodeWithText(R.string.search_previous_searches).assertIsDisplayed()
        onNodeWithText(movies[0].title).assertIsDisplayed()
        onNodeWithText(movies[1].title).assertIsDisplayed()
    }

    @Test
    fun givenAnEmptyQuery_PlaceHolderContentIsShown(): Unit = with(composeTestRule) {
        val previousSearchesUiState = PreviousSearchesUiState.Success(MovieMock.movies)
        val searchesUiState = SearchResultUiState.EmptyQuery
        setContent {
            Screen {
                SearchScreen(
                    previousSearchesUiState,
                    searchesUiState,
                    "",
                    true,
                    {},
                    {},
                    {},
                    {}
                )
            }
        }

        onNodeWithText(R.string.search_content_placeholder).assertIsDisplayed()
    }

    @Test
    fun givenANonEmptySearchResults_ResultAreShown(): Unit = with(composeTestRule) {
        val movies = MovieMock.movies
        val previousSearchesUiState = PreviousSearchesUiState.Success(emptyList())
        val searchesUiState = SearchResultUiState.Success(movies)
        setContent {
            Screen {
                SearchScreen(
                    previousSearchesUiState,
                    searchesUiState,
                    "Movie",
                    true,
                    {},
                    {},
                    {},
                    {}
                )
            }
        }

        onNodeWithText(R.string.search_main_results).assertIsDisplayed()
        onNodeWithText(movies[0].title).assertIsDisplayed()
        onNodeWithText(movies[1].title).assertIsDisplayed()
    }

    @Test
    fun givenAnEmptySearchResult_NoResultAreFoundIsShown(): Unit = with(composeTestRule) {
        val previousSearchesUiState = PreviousSearchesUiState.Success(emptyList())
        val searchesUiState = SearchResultUiState.Success(emptyList())
        setContent {
            Screen {
                SearchScreen(
                    previousSearchesUiState,
                    searchesUiState,
                    "Movii",
                    true,
                    {},
                    {},
                    {},
                    {}
                )
            }
        }

        onNodeWithText(R.string.search_no_results).assertIsDisplayed()
    }

    @Test
    fun givenASearchResultError_ErrorScreenIsShown(): Unit = with(composeTestRule) {
        val previousSearchesUiState = PreviousSearchesUiState.Success(emptyList())
        val searchesUiState =
            SearchResultUiState.Error(AsyncException.ConnectionError("Error connecting with host"))
        setContent {
            Screen {
                SearchScreen(
                    previousSearchesUiState,
                    searchesUiState,
                    "Movie",
                    true,
                    {},
                    {},
                    {},
                    {}
                )
            }
        }

        onNodeWithText(commonRes.string.connection_error).assertIsDisplayed()
    }
}