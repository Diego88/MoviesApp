package com.dmoyahur.moviesapp.data.repository.search

import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchLocalDataSource
import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchRemoteDataSource
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchRepositoryTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @MockK
    lateinit var remoteDataSource: SearchRemoteDataSource

    @MockK
    lateinit var localDataSource: SearchLocalDataSource

    private lateinit var repository: SearchRepository

    private val movies = MovieMock.movies

    companion object {
        private const val MAX_HISTORY_SIZE = 12
    }

    @Before
    fun setUp() {
        coEvery { localDataSource.previousSearches } returns flowOf(movies)
        coEvery { localDataSource.findMovieSearchById(any()) } returns flowOf(movies.first())
        coEvery { remoteDataSource.fetchMovieById(any()) } returns movies.first()
        coJustRun { localDataSource.saveMovieSearch(any()) }

        repository = SearchRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `when previousSearches is called, then return previous searches from local data source`() {
        val expectedMovies = movies

        val actual = runBlocking { repository.previousSearches.first() }

        coVerify { localDataSource.previousSearches }
        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when findMovieSearchById is called, then return updated movie from local data source`() {
        val expectedMovie = movies.first()

        val actual = runBlocking { repository.findMovieSearchById(1).first() }

        coVerifyOrder {
            localDataSource.findMovieSearchById(1)
            remoteDataSource.fetchMovieById(1)
            localDataSource.saveMovieSearch(expectedMovie)
        }
        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when findMovieSearchById is called and local movies count is greater than max count, then delete oldest searches`() {
        val movies = MovieMock.getMoviesBySize(MAX_HISTORY_SIZE + 1)
        val expectedMovie = movies.first().copy(id = 15)
        coEvery { localDataSource.findMovieSearchById(any()) } returns flowOf(expectedMovie)
        coEvery { remoteDataSource.fetchMovieById(any()) } returns expectedMovie
        coEvery { localDataSource.getMoviesSearchCount() } returns movies.size
        coJustRun { localDataSource.deleteOldestSearches(any()) }

        val actual = runBlocking { repository.findMovieSearchById(15).first() }

        coVerifySequence {
            localDataSource.previousSearches // Called automatically
            localDataSource.findMovieSearchById(15)
            localDataSource.saveMovieSearch(expectedMovie)
            localDataSource.getMoviesSearchCount()
            localDataSource.deleteOldestSearches(1)
        }
        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when fetchMovieSearchById fails and local movie is not empty, then return local movie`() {
        val expectedMovie = movies.first()
        coEvery { remoteDataSource.fetchMovieById(any()) } throws AsyncException.ConnectionError("Connection error")

        val actual = runBlocking { repository.findMovieSearchById(1).first() }

        coVerifyOrder {
            localDataSource.findMovieSearchById(1)
            remoteDataSource.fetchMovieById(1)
        }
        coVerify(exactly = 0) { localDataSource.saveMovieSearch(any()) }
        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when fetchMovieSearchById fails and local movie is null, then throw exception`() {
        val expectedException = AsyncException.ConnectionError("Connection error")
        coEvery { localDataSource.findMovieSearchById(any()) } returns flowOf(null)
        coEvery { remoteDataSource.fetchMovieById(any()) } throws expectedException

        assertThrows(expectedException.debugMessage, expectedException::class.java) {
            runBlocking { repository.findMovieSearchById(1).first() }
        }
        coVerifyOrder {
            localDataSource.findMovieSearchById(1)
            remoteDataSource.fetchMovieById(1)
        }
        coVerify(exactly = 0) { localDataSource.saveMovieSearch(any()) }
    }

    @Test
    fun `when searchMovie is called, then return movies from remote data source`() {
        val query = "Movie 1"
        val expectedMovies = listOf(movies.first(), movies.last())
        coEvery { remoteDataSource.searchMovie(query) } returns expectedMovies

        val actual = runBlocking { repository.searchMovie(query).first() }

        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when searchMovie fails, then throw exception`() {
        val query = "Movie"
        val expectedException = AsyncException.ConnectionError("Connection error")
        coEvery { remoteDataSource.searchMovie(query) } throws expectedException

        assertThrows(expectedException.debugMessage, expectedException::class.java) {
            runBlocking { repository.searchMovie(query).first() }
        }
    }

    @Test
    fun `when deleteMovieSearch is called, then delete movie search from local data source`() {
        coJustRun { localDataSource.deleteMovieSearch(any()) }

        runBlocking { repository.deleteMovieSearch(1) }

        coVerify { localDataSource.deleteMovieSearch(1) }
    }
}