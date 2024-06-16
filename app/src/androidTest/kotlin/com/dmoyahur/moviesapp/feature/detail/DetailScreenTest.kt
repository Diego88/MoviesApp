package com.dmoyahur.moviesapp.feature.detail

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dmoyahur.moviesapp.MainActivity
import com.dmoyahur.moviesapp.R
import com.dmoyahur.moviesapp.common.util.TestConstants
import com.dmoyahur.moviesapp.feature.movies.clickOnMovie
import com.dmoyahur.moviesapp.feature.search.clickOnSearchResult
import com.dmoyahur.moviesapp.feature.search.performSearch
import com.dmoyahur.moviesapp.testShared.utils.assertTagExists
import com.dmoyahur.moviesapp.testShared.utils.assertTagIsDisplayed
import com.dmoyahur.moviesapp.testShared.utils.clickOnNodeWithTag
import com.dmoyahur.moviesapp.testShared.utils.clickOnNodeWithText
import com.dmoyahur.moviesapp.testShared.utils.hasText
import com.dmoyahur.moviesapp.utils.InstrumentedTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
@HiltAndroidTest
class DetailScreenTest : InstrumentedTest() {

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun detailsScreen_movieDetailsAreDisplayed(): Unit = with(composeTestRule) {
        clickOnMovie(0)

        assertTagExists(TestConstants.Detail.ORIGINAL_TITLE_TAG)
        assertTagExists(TestConstants.Detail.OVERVIEW_TAG)
        assertTagExists(TestConstants.Detail.ORIGINAL_LANGUAGE_TAG)
        assertTagExists(TestConstants.Detail.RELEASE_DATE_TAG)
        assertTagExists(TestConstants.Detail.POPULARITY_TAG)
        assertTagExists(TestConstants.Detail.VOTE_AVERAGE_TAG)
    }

    @Test
    fun detailsScreen_navigationBarIsNotDisplayed(): Unit = with(composeTestRule) {
        clickOnMovie(0)

        waitUntilDoesNotExist(hasText(R.string.movies))
    }

    @Test
    fun detailsScreen_clickOnBackButton_returnsToMoviesScreen(): Unit = with(composeTestRule) {
        clickOnMovie(0)

        clickOnNodeWithTag(TestConstants.Common.BACK_BUTTON_TAG)

        assertTagIsDisplayed(TestConstants.Movies.MOVIES_LIST_TAG)
    }

    @Test
    fun detailScreen_back_returnsSearchResults(): Unit = with(composeTestRule) {
        clickOnNodeWithText(R.string.search)

        performSearch("the")
        clickOnSearchResult(1)
        clickOnNodeWithTag(TestConstants.Common.BACK_BUTTON_TAG)

        assertTagIsDisplayed(TestConstants.Search.SEARCH_RESULT_LIST_TAG)
    }
}