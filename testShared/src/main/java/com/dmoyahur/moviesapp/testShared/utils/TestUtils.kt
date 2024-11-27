package com.dmoyahur.moviesapp.testShared.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.MockResponse

fun readResourceFile(path: String): String {
    return object {}.javaClass.classLoader?.getResource(path)?.readText().orEmpty()
}

fun MockResponse.fromJson(jsonFile: String): MockResponse =
    setBody(readResourceFile(jsonFile))

internal fun getString(@StringRes id: Int) = getTargetContext().getString(id)

internal fun getTargetContext(): Context = InstrumentationRegistry.getInstrumentation().targetContext