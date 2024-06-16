package com.dmoyahur.moviesapp.testShared.testrules

import com.dmoyahur.moviesapp.testShared.utils.fromJson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule : TestWatcher() {

    private lateinit var server: MockWebServer

    override fun starting(description: Description?) {
        server = MockWebServer()
        server.dispatcher = MockDispatcher()
        server.start(8080)
    }

    override fun finished(description: Description?) {
        server.shutdown()
    }
}

private class MockDispatcher : Dispatcher() {
    override fun dispatch(request: okhttp3.mockwebserver.RecordedRequest): MockResponse {
        val requestUrl = request.requestUrl
        return when (requestUrl?.pathSegments) {
            listOf("movie", "popular") -> MockResponse().fromJson("movie_popular.json")
            listOf("movie", "653346") -> MockResponse().fromJson("movie_id.json")
            listOf("search", "movie") -> {
                val query = requestUrl.queryParameter("query")
                if (query == "XXX") {
                    MockResponse().fromJson("search_movie_empty.json")
                } else {
                    MockResponse().fromJson("search_movie.json")
                }
            }
            else -> MockResponse().setResponseCode(404)
        }
    }
}