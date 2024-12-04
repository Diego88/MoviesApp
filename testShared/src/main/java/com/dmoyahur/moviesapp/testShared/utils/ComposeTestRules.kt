@file:OptIn(ExperimentalTestApi::class)

package com.dmoyahur.moviesapp.testShared.utils

import androidx.annotation.StringRes
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule

fun ComposeTestRule.waitUntilTagIsDisplayed(tag: String) {
    waitUntilExactlyOneExists(hasTestTag(tag))
}

fun ComposeTestRule.waitUntilTagDoesNotExists(tag: String) {
    waitUntilDoesNotExist(hasTestTag(tag))
}

fun ComposeTestRule.waitUntilContentDescriptionIsDisplayed(@StringRes id: Int) {
    waitUntilExactlyOneExists(hasContentDescription(id))
}