package com.dmoyahur.moviesapp.data.remote.search

import com.dmoyahur.moviesapp.data.remote.movies.dto.MovieDto
import com.dmoyahur.moviesapp.data.remote.movies.dto.MoviesResponseDto
import com.dmoyahur.moviesapp.testShared.utils.readResourceFile
import io.mockk.junit4.MockKRule
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class SearchNetworkApiTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @Inject
    lateinit var api: SearchNetworkApi

    private lateinit var json: Json

    @Before
    fun setUp() {
        json = Json { ignoreUnknownKeys = true }
    }

    @Test
    fun `when fetchMovieById is called, should parse json response`() {
        val moviesJsonResponse = readResourceFile("movie_id.json")
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

        val actual = json.decodeFromString(MovieDto.serializer(), moviesJsonResponse)

        Assert.assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when searchMovie is called, should parse json response`() {
        val moviesJsonResponse = readResourceFile("search_movie.json")
        val expectedMovies = listOf(
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

        val actual = json.decodeFromString(MoviesResponseDto.serializer(), moviesJsonResponse)

        Assert.assertEquals(1, actual.page)
        Assert.assertEquals(1, actual.totalPages)
        Assert.assertEquals(2, actual.totalResults)
        Assert.assertEquals(expectedMovies, actual.results)
    }

    @Test
    fun `when searchMovie is called and result is empty, should parse json response`() {
        val moviesJsonResponse = readResourceFile("search_movie_empty.json")
        val expectedMovies = emptyList<MovieDto>()

        val actual = json.decodeFromString(MoviesResponseDto.serializer(), moviesJsonResponse)

        Assert.assertEquals(1, actual.page)
        Assert.assertEquals(1, actual.totalPages)
        Assert.assertEquals(0, actual.totalResults)
        Assert.assertEquals(expectedMovies, actual.results)
    }
}