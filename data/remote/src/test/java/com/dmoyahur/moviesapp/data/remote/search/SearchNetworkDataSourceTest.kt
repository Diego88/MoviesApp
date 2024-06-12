package com.dmoyahur.moviesapp.data.remote.search

import com.dmoyahur.moviesapp.data.remote.MovieDtoMock
import com.dmoyahur.moviesapp.data.remote.movies.mapper.MovieDtoMapper
import com.dmoyahur.moviesapp.model.error.AsyncException
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

class SearchNetworkDataSourceTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var searchApi: SearchNetworkApi

    private lateinit var dataSource: SearchNetworkDataSource

    @Before
    fun setUp() {
        dataSource = SearchNetworkDataSource(searchApi)
    }

    @Test
    fun `when request a movie by its id, then return movie`() {
        val response = MovieDtoMock.moviesDto.first()
        val expectedMovie = MovieDtoMapper.mapToDomain(response)
        coEvery { searchApi.fetchMovieById(1) } returns response

        val actual = runBlocking { dataSource.fetchMovieById(1) }

        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when request popular movies fails, then throw an exception`() {
        coEvery { searchApi.fetchMovieById(1) } throws UnknownHostException("Host cannot be resolved")

        assertThrows("Host cannot be resolved", AsyncException.ConnectionError::class.java) {
            runBlocking { dataSource.fetchMovieById(1) }
        }
    }

    @Test
    fun `when request a movie search, then return a list of movies`() {
        val query = "Movie"
        val response = MovieDtoMock.moviesResponseDto
        val expectedMovies = response.results.map { MovieDtoMapper.mapToDomain(it) }
        coEvery { searchApi.searchMovie(query) } returns response

        val actual = runBlocking { dataSource.searchMovie(query) }

        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when request a movie search fails, then throw an exception`() {
        val query = "Movie"
        coEvery { searchApi.searchMovie(query) } throws UnknownHostException("Host cannot be resolved")

        assertThrows("Host cannot be resolved", AsyncException.ConnectionError::class.java) {
            runBlocking { dataSource.searchMovie(query) }
        }
    }
}