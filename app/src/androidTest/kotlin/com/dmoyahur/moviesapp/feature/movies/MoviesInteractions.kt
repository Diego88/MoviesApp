package com.dmoyahur.moviesapp.feature.movies

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.dmoyahur.moviesapp.common.util.TestConstants
import com.dmoyahur.moviesapp.testShared.utils.clickOnChildNodeWithIndex
import com.dmoyahur.moviesapp.testShared.utils.scrollToNodeWithMatcher
import com.dmoyahur.moviesapp.testShared.utils.waitUntilTagIsDisplayed

internal fun ComposeTestRule.clickOnMovie(index: Int) {
    waitUntilTagIsDisplayed(TestConstants.Movies.MOVIES_LIST_TAG)
    clickOnChildNodeWithIndex(TestConstants.Movies.MOVIES_LIST_TAG, index)
}

internal fun SemanticsNodeInteractionsProvider.scrollToMovie(matcher: SemanticsMatcher) {
    scrollToNodeWithMatcher(matcher)
}