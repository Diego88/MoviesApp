package com.dmoyahur.moviesapp.testShared.utils

import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText

fun hasText(
    @StringRes id: Int,
    substring: Boolean = false,
    ignoreCase: Boolean = false
): SemanticsMatcher {
    return hasText(getString(id), substring, ignoreCase)
}

fun hasContentDescription(
    @StringRes id: Int,
    substring: Boolean = false,
    ignoreCase: Boolean = false
): SemanticsMatcher {
    return hasContentDescription(getString(id), substring, ignoreCase)
}