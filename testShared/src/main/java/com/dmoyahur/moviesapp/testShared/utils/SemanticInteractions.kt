package com.dmoyahur.moviesapp.testShared.utils

import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode

fun SemanticsNodeInteractionsProvider.onNodeWithText(
    @StringRes id: Int,
    substring: Boolean = false,
    ignoreCase: Boolean = false,
    useUnmergedTree: Boolean = false
): SemanticsNodeInteraction {
    return onNodeWithText(getString(id), substring, ignoreCase, useUnmergedTree)
}

fun SemanticsNodeInteractionsProvider.onNodeWithContentDescription(
    @StringRes id: Int,
    substring: Boolean = false,
    ignoreCase: Boolean = false,
    useUnmergedTree: Boolean = false
): SemanticsNodeInteraction {
    return onNodeWithContentDescription(getString(id), substring, ignoreCase, useUnmergedTree)
}

fun SemanticsNodeInteractionsProvider.clickOnNodeWithText(@StringRes id: Int) {
    onNodeWithText(id).performClick()
}

fun SemanticsNodeInteractionsProvider.clickOnNodeWithTag(tag: String) {
    onNodeWithTag(tag).performClick()
}

fun SemanticsNodeInteractionsProvider.clickOnNodeWithContentDescription(@StringRes id: Int) {
    onNodeWithContentDescription(id).performClick()
}

fun SemanticsNodeInteractionsProvider.clickOnChildNodeWithIndex(
    parentTag: String,
    childIndex: Int
) {
    onNodeWithTag(parentTag).onChildAt(childIndex).performClick()
}

fun SemanticsNodeInteractionsProvider.scrollToNodeWithMatcher(matcher: SemanticsMatcher) {
    onAllNodes(hasScrollToNodeAction())
        .onFirst()
        .performScrollToNode(matcher)
}

fun SemanticsNodeInteractionsProvider.assertTextIsDisplayed(text: String) {
    onNodeWithText(text).assertIsDisplayed()
}

fun SemanticsNodeInteractionsProvider.assertTextIsDisplayed(@StringRes id: Int) {
    onNodeWithText(id).assertIsDisplayed()
}

fun SemanticsNodeInteractionsProvider.assertTextIsSelected(@StringRes id: Int) {
    onNodeWithText(id).assertIsSelected()
}

fun SemanticsNodeInteractionsProvider.assertTagIsDisplayed(tag: String) {
    onNodeWithTag(tag).assertIsDisplayed()
}

fun SemanticsNodeInteractionsProvider.assertTagExists(tag: String) {
    onNodeWithTag(tag).assertExists()
}

fun SemanticsNodeInteractionsProvider.assertTagDoesNotExists(tag: String) {
    onNodeWithTag(tag).assertDoesNotExist()
}