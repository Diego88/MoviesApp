package com.dmoyahur.moviesapp.data.remote.movies

import com.dmoyahur.moviesapp.data.remote.movies.dto.MovieDto
import com.dmoyahur.moviesapp.data.remote.movies.dto.MoviesResponseDto
import com.dmoyahur.moviesapp.testShared.utils.readResourceFile
import io.mockk.junit4.MockKRule
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class MoviesNetworkApiTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @Inject
    lateinit var api: MoviesNetworkApi

    private lateinit var json: Json

    @Before
    fun setUp() {
        json = Json { ignoreUnknownKeys = true }
    }

    @Test
    fun `when getPopularMovies is called, should parse json response`() {
        val moviesJsonResponse = readResourceFile("movie_popular.json")
        val expectedMovie = MovieDto(
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

        val actual = json.decodeFromString(MoviesResponseDto.serializer(), moviesJsonResponse)

        assertEquals(1, actual.page)
        assertEquals(44631, actual.totalPages)
        assertEquals(892612, actual.totalResults)
        assertEquals(20, actual.results.size)
        assertEquals(expectedMovie, actual.results.first())
    }
}