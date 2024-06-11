package com.dmoyahur.moviesapp.data.repository.movies

import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesLocalDataSource
import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesRemoteDataSource
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesRepositoryTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @MockK
    lateinit var remoteDataSource: MoviesRemoteDataSource

    @MockK
    lateinit var localDataSource: MoviesLocalDataSource

    private lateinit var repository: MoviesRepository

    private val movies = MovieMock.movies

    @Before
    fun setUp() {
        coEvery { remoteDataSource.fetchPopularMovies() } returns movies
        coEvery { localDataSource.movies } returns flowOf(movies)

        repository = MoviesRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `when movies is called, then return updated movies`() {
        val expectedMovies = movies
        coJustRun { localDataSource.saveMovies(any()) }

        val actual = runBlocking { repository.movies.first() }

        coVerifySequence {
            localDataSource.movies
            remoteDataSource.fetchPopularMovies()
            localDataSource.saveMovies(expectedMovies)
        }
        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when fetchPopularMovies fails and local movies is not empty, then return local movies`() {
        val expectedMovies = movies
        coEvery { remoteDataSource.fetchPopularMovies() } throws AsyncException.ConnectionError("Connection error")

        val actual = runBlocking { repository.movies.first() }

        coVerifyOrder {
            remoteDataSource.fetchPopularMovies()
            localDataSource.movies
        }
        coVerify(exactly = 0) { localDataSource.saveMovies(any()) }
        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when fetchPopularMovies fails and local movies is empty, then throw exception`() {
        val expectedException = AsyncException.ConnectionError("Connection error")
        coEvery { remoteDataSource.fetchPopularMovies() } throws expectedException
        coEvery { localDataSource.movies } returns flowOf(emptyList())

        assertThrows(expectedException.debugMessage, expectedException::class.java) {
            runBlocking { repository.movies.first() }
        }
        coVerifyOrder {
            remoteDataSource.fetchPopularMovies()
            localDataSource.movies
        }
        coVerify(exactly = 0) { localDataSource.saveMovies(any()) }
    }

    @Test
    fun `when findMovieById is called, then return movie from local data source`() {
        val expectedMovie = movies.first()
        coEvery { localDataSource.findMovieById(1) } returns flowOf(expectedMovie)

        val actual = runBlocking { repository.findMovieById(1).first() }

        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when findMovieById is called and result is null, then repository does not emit result`() {
        coEvery { localDataSource.findMovieById(1) } returns flowOf(null)

        val actual = runBlocking { repository.findMovieById(1).count() }

        assertEquals(0, actual)
    }
}