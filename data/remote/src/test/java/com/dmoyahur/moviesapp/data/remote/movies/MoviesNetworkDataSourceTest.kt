package com.dmoyahur.moviesapp.data.remote.movies

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

class MoviesNetworkDataSourceTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var moviesApi: MoviesNetworkApi

    private lateinit var dataSource: MoviesNetworkDataSource

    @Before
    fun setUp() {
        dataSource = MoviesNetworkDataSource(moviesApi)
    }

    @Test
    fun `when request popular movies, then return a list of popular movies`() {
        val response = MovieDtoMock.moviesResponseDto
        val expectedMovies = response.results.map { MovieDtoMapper.mapToDomain(it) }
        coEvery { moviesApi.fetchPopularMovies() } returns response

        val actual = runBlocking { dataSource.fetchPopularMovies() }

        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when request popular movies fails, then throw an exception`() {
        coEvery { moviesApi.fetchPopularMovies() } throws UnknownHostException("Host cannot be resolved")

        assertThrows("Host cannot be resolved", AsyncException.ConnectionError::class.java) {
            runBlocking { dataSource.fetchPopularMovies() }
        }
    }
}