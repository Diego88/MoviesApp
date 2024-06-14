package com.dmoyahur.moviesapp.testShared.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasText
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.MockResponse

fun hasText(
    @StringRes id: Int,
    substring: Boolean = false,
    ignoreCase: Boolean = false,
): SemanticsMatcher {
    val text = getTargetContext().getString(id)
    return hasText(text, substring, ignoreCase)
}

fun SemanticsNodeInteractionsProvider.onNodeWithText(
    @StringRes id: Int,
    substring: Boolean = false,
    ignoreCase: Boolean = false,
    useUnmergedTree: Boolean = false,
): SemanticsNodeInteraction {
    return onNode(hasText(id, substring, ignoreCase), useUnmergedTree)
}

fun readResourceFile(path: String): String {
    return object {}.javaClass.classLoader?.getResource(path)?.readText() ?: ""
}

fun MockResponse.fromJson(jsonFile: String): MockResponse =
    setBody(readResourceFile(jsonFile))

private fun getTargetContext(): Context = InstrumentationRegistry.getInstrumentation().targetContext