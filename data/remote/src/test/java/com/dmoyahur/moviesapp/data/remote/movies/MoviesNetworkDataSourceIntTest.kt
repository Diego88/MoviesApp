package com.dmoyahur.moviesapp.data.remote.movies

import android.os.Build
import com.dmoyahur.moviesapp.data.remote.movies.dto.MovieDto
import com.dmoyahur.moviesapp.data.remote.movies.mapper.MovieDtoMapper
import com.dmoyahur.moviesapp.testShared.testrules.MockWebServerRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    application = HiltTestApplication::class,
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.UPSIDE_DOWN_CAKE]
)
class MoviesNetworkDataSourceIntTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @Inject
    lateinit var moviesApi: MoviesNetworkApi

    private lateinit var dataSource: MoviesNetworkDataSource

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer.start()
        dataSource = MoviesNetworkDataSource(moviesApi)
    }

    @Test
    fun `when request popular movies, then return a list of popular movies`() {
        val movieDto = MovieDto(
            id = 653346,
            originalLanguage = "en",
            originalTitle = "Kingdom of the Planet of the Apes",
            overview = "Several generations in the future following Caesar's reign, apes are now the dominant species and live harmoniously while humans have been reduced to living in the shadows. As a new tyrannical ape leader builds his empire, one young ape undertakes a harrowing journey that will cause him to question all that he has known about the past and to make choices that will define a future for apes and humans alike.",
            popularity = 4050.674,
            posterPath = "/gKkl37BQuKTanygYQG1pyYgLVgf.jpg",
            backdropPath = "/fqv8v6AycXKsivp1T5yKtLbGXce.jpg",
            releaseDate = "2024-05-08",
            title = "Kingdom of the Planet of the Apes",
            voteAverage = 6.868
        )
        val expectedMovie = MovieDtoMapper.mapToDomain(movieDto)

        val actual = runBlocking { dataSource.fetchPopularMovies() }

        assertEquals(20, actual.size)
        assertEquals(expectedMovie, actual.first())
    }
}