package com.dmoyahur.moviesapp.feature.search

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.performTextInput
import com.dmoyahur.moviesapp.common.util.TestConstants
import com.dmoyahur.moviesapp.testShared.utils.clickOnChildNodeWithIndex
import com.dmoyahur.moviesapp.testShared.utils.clickOnNodeWithContentDescription
import com.dmoyahur.moviesapp.testShared.utils.onNodeWithText
import com.dmoyahur.moviesapp.testShared.utils.waitUntilContentDescriptionIsDisplayed
import com.dmoyahur.moviesapp.testShared.utils.waitUntilTagIsDisplayed

internal fun SemanticsNodeInteractionsProvider.performSearch(query: String) {
    onNodeWithText(R.string.search_placeholder).performTextInput(query)
}

internal fun ComposeTestRule.clickOnDeleteButton() {
    waitUntilContentDescriptionIsDisplayed(R.string.search_delete)
    clickOnNodeWithContentDescription(R.string.search_delete)
}

internal fun ComposeTestRule.clickOnPreviousSearch(index: Int) {
    waitUntilTagIsDisplayed(TestConstants.Search.PREVIOUS_SEARCHES_LIST_TAG)
    clickOnChildNodeWithIndex(TestConstants.Search.PREVIOUS_SEARCHES_LIST_TAG, index)
}

internal fun ComposeTestRule.clickOnSearchResult(index: Int) {
    waitUntilTagIsDisplayed(TestConstants.Search.SEARCH_RESULT_LIST_TAG)
    clickOnChildNodeWithIndex(TestConstants.Search.SEARCH_RESULT_LIST_TAG, index)
}

internal fun ComposeTestRule.clickOnClearQuery() {
    clickOnNodeWithContentDescription(R.string.search_clear)
}

internal fun ComposeTestRule.closeSearch() {
    clickOnClearQuery()
    clickOnClearQuery()
}