package com.dmoyahur.moviesapp.feature.detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.dmoyahur.moviesapp.common.ui.components.Screen
import com.dmoyahur.moviesapp.feature.detail.ui.DetailScreen
import com.dmoyahur.moviesapp.feature.detail.ui.DetailUiState
import com.dmoyahur.moviesapp.feature.detail.util.DetailConstants
import com.dmoyahur.moviesapp.testShared.MovieMock
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule()
    val composeTestRule = createComposeRule()

    @Test
    fun givenAMovie_MovieDetailIsShown(): Unit = with(composeTestRule) {
        val movie = MovieMock.movies.first()

        setContent {
            Screen {
                DetailScreen(state = DetailUiState(movie = movie), onBack = {})
            }
        }

        onNodeWithTag(DetailConstants.OVERVIEW_TAG).assertIsDisplayed()
        onNodeWithTag(DetailConstants.ORIGINAL_LANGUAGE_TAG).assertIsDisplayed()
        onNodeWithTag(DetailConstants.MOVIE_TITLE_TAG).assertIsDisplayed()
        onNodeWithTag(DetailConstants.RELEASE_DATE_TAG).assertIsDisplayed()
        onNodeWithTag(DetailConstants.POPULARITY_TAG).assertIsDisplayed()
        onNodeWithTag(DetailConstants.VOTE_AVERAGE_TAG).assertIsDisplayed()
    }

    @Test
    fun givenAnError_ErrorScreenIsShown(): Unit = with(composeTestRule) {
        setContent {
            Screen {
                DetailScreen(state = DetailUiState(error = Throwable()), onBack = {})
            }
        }

        onNodeWithText("An error occurred").assertIsDisplayed()
    }
}