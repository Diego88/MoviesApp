package com.dmoyahur.moviesapp.data.remote.search

import android.os.Build
import com.dmoyahur.moviesapp.data.remote.movies.dto.MovieDto
import com.dmoyahur.moviesapp.data.remote.movies.mapper.MovieDtoMapper
import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.testrules.MockWebServerRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
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
class SearchNetworkDataSourceIntTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @Inject
    lateinit var searchApi: SearchNetworkApi

    private lateinit var dataSource: SearchNetworkDataSource

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer.start()
        dataSource = SearchNetworkDataSource(searchApi)
    }

    @Test
    fun `when request a movie by id, then return movie`() {
        val response = MovieDto(
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
        val expectedMovie = MovieDtoMapper.mapToDomain(response)

        val actual = runBlocking { dataSource.fetchMovieById(653346) }

        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when request a movie by id fails, then throw an exception`() {
        assertThrows(AsyncException.ConnectionError::class.java) {
            runBlocking { dataSource.fetchMovieById(0) }
        }
    }

    @Test
    fun `when request a movie search, then return a list of movies`() {
        val query = "Movie"
        val response = listOf(
            MovieDto(
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
            ),
            MovieDto(
                id = 823464,
                originalLanguage = "en",
                originalTitle = "Godzilla x Kong: The New Empire",
                overview = "Following their explosive showdown, Godzilla and Kong must reunite against a colossal undiscovered threat hidden within our world, challenging their very existence – and our own.",
                popularity = 1852.222,
                posterPath = "/z1p34vh7dEOnLDmyCrlUVLuoDzd.jpg",
                backdropPath = "/xRd1eJIDe7JHO5u4gtEYwGn5wtf.jpg",
                releaseDate = "2024-03-27",
                title = "Godzilla x Kong: The New Empire",
                voteAverage = 7.212
            )
        )
        val expectedMovies = response.map { MovieDtoMapper.mapToDomain(it) }

        val actual = runBlocking { dataSource.searchMovie(query) }

        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when a movie search does not have result, then return an empty list`() {
        val actual = runBlocking { dataSource.searchMovie("") }

        assertEquals(emptyList<MovieBo>(), actual)
    }
}