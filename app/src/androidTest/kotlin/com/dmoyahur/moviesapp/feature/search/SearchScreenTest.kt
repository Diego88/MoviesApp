package com.dmoyahur.moviesapp.feature.search

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dmoyahur.moviesapp.MainActivity
import com.dmoyahur.moviesapp.R
import com.dmoyahur.moviesapp.common.util.TestConstants
import com.dmoyahur.moviesapp.testShared.utils.assertTagDoesNotExists
import com.dmoyahur.moviesapp.testShared.utils.assertTagIsDisplayed
import com.dmoyahur.moviesapp.testShared.utils.assertTextIsDisplayed
import com.dmoyahur.moviesapp.testShared.utils.clickOnNodeWithTag
import com.dmoyahur.moviesapp.testShared.utils.clickOnNodeWithText
import com.dmoyahur.moviesapp.testShared.utils.waitUntilTagDoesNotExists
import com.dmoyahur.moviesapp.testShared.utils.waitUntilTagIsDisplayed
import com.dmoyahur.moviesapp.utils.InstrumentedTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import com.dmoyahur.moviesapp.feature.search.R as SearchR

@HiltAndroidTest
class SearchScreenTest : InstrumentedTest() {

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun searchScreen_typeSearch_showsResults(): Unit = with(composeTestRule) {
        clickOnNodeWithText(R.string.search)

        performSearch("the")

        waitUntilTagIsDisplayed(TestConstants.Search.SEARCH_RESULT_LIST_TAG)
    }

    @Test
    fun searchScreen_typeBadSearch_showsNoResults(): Unit = with(composeTestRule) {
        clickOnNodeWithText(R.string.search)

        performSearch(TestConstants.Search.QUERY_NO_RESULTS)

        assertTagDoesNotExists(TestConstants.Search.SEARCH_RESULT_LIST_TAG)
        assertTextIsDisplayed(SearchR.string.search_no_results)
    }

    @Test
    fun searchScreen_clickOnResult_detailsScreenIsDisplayed(): Unit = with(composeTestRule) {
        clickOnNodeWithText(R.string.search)

        performSearch("the")
        clickOnSearchResult(1) // Element with index 0 is header

        waitUntilTagIsDisplayed(TestConstants.Detail.ORIGINAL_TITLE_TAG)
        waitUntilTagIsDisplayed(TestConstants.Detail.OVERVIEW_TAG)
    }

    @Test
    fun searchScreen_clickOnClear_emptyScreenIsDisplayed(): Unit = with(composeTestRule) {
        clickOnNodeWithText(R.string.search)

        performSearch("the")
        clickOnClearQuery()

        waitUntilTagDoesNotExists(TestConstants.Search.SEARCH_RESULT_LIST_TAG)
    }

    @Test
    fun searchScreen_previousSearchesAreDisplayed(): Unit = with(composeTestRule) {
        clickOnNodeWithText(R.string.search)

        performSearch("the")
        clickOnSearchResult(1)
        clickOnNodeWithTag(TestConstants.Common.BACK_BUTTON_TAG)
        closeSearch()

        assertTagIsDisplayed(TestConstants.Search.PREVIOUS_SEARCHES_LIST_TAG)
    }

    @Test
    fun searchScreen_clickOnPreviousSearch_detailsScreenIsDisplayed(): Unit =
        with(composeTestRule) {
            clickOnNodeWithText(R.string.search)

            performSearch("The")
            clickOnSearchResult(1)
            clickOnNodeWithTag(TestConstants.Common.BACK_BUTTON_TAG)
            closeSearch()
            clickOnPreviousSearch(1)

            waitUntilTagIsDisplayed(TestConstants.Detail.ORIGINAL_TITLE_TAG)
            waitUntilTagIsDisplayed(TestConstants.Detail.OVERVIEW_TAG)
        }

    @Test
    fun searchScreen_deletePreviousSearch_previousSearchIsNotDisplayed(): Unit =
        with(composeTestRule) {
            clickOnNodeWithText(R.string.search)

            performSearch("The")
            clickOnSearchResult(1)
            clickOnNodeWithTag(TestConstants.Common.BACK_BUTTON_TAG)
            closeSearch()
            clickOnDeleteButton()

            waitUntilTagDoesNotExists(TestConstants.Search.PREVIOUS_SEARCHES_LIST_TAG)
        }
}