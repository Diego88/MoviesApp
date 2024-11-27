package com.dmoyahur.moviesapp.feature.detail

import androidx.compose.ui.test.junit4.createComposeRule
import com.dmoyahur.moviesapp.common.ui.components.Screen
import com.dmoyahur.moviesapp.common.util.TestConstants
import com.dmoyahur.moviesapp.feature.detail.ui.DetailScreen
import com.dmoyahur.moviesapp.feature.detail.ui.DetailUiState
import com.dmoyahur.moviesapp.testShared.MovieMock
import com.dmoyahur.moviesapp.testShared.utils.assertTagIsDisplayed
import com.dmoyahur.moviesapp.testShared.utils.assertTextIsDisplayed
import org.junit.Rule
import org.junit.Test
import com.dmoyahur.moviesapp.common.R as commonRes

class DetailUnitTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenAMovie_MovieDetailIsShown(): Unit = with(composeTestRule) {
        setContent {
            Screen {
                DetailScreen(
                    state = DetailUiState.Success(movie = MovieMock.movies.first()),
                    onBack = {}
                )
            }
        }

        assertTagIsDisplayed(TestConstants.Detail.OVERVIEW_TAG)
        assertTagIsDisplayed(TestConstants.Detail.ORIGINAL_LANGUAGE_TAG)
        assertTagIsDisplayed(TestConstants.Detail.ORIGINAL_TITLE_TAG)
        assertTagIsDisplayed(TestConstants.Detail.RELEASE_DATE_TAG)
        assertTagIsDisplayed(TestConstants.Detail.POPULARITY_TAG)
        assertTagIsDisplayed(TestConstants.Detail.VOTE_AVERAGE_TAG)
    }

    @Test
    fun givenAnError_ErrorScreenIsShown(): Unit = with(composeTestRule) {
        setContent {
            Screen {
                DetailScreen(state = DetailUiState.Error(Throwable()), onBack = {})
            }
        }

        assertTextIsDisplayed(commonRes.string.generic_error)
    }
}