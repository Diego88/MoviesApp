package com.dmoyahur.moviesapp.testShared.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasText
import androidx.test.platform.app.InstrumentationRegistry

@SuppressLint("StaticFieldLeak")
val targetCtx: Context = InstrumentationRegistry.getInstrumentation().targetContext

fun hasText(
    @StringRes id: Int,
    substring: Boolean = false,
    ignoreCase: Boolean = false
): SemanticsMatcher {
    val text = targetCtx.getString(id)
    return hasText(text, substring, ignoreCase)
}

fun SemanticsNodeInteractionsProvider.onNodeWithText(
    @StringRes id: Int,
    substring: Boolean = false,
    ignoreCase: Boolean = false,
    useUnmergedTree: Boolean = false
): SemanticsNodeInteraction {
    return onNode(hasText(id, substring, ignoreCase), useUnmergedTree)
}