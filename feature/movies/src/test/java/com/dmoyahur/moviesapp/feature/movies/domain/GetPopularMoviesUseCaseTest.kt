package com.dmoyahur.moviesapp.feature.movies.domain

import com.dmoyahur.moviesapp.data.repository.movies.MoviesRepository
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetPopularMoviesUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: MoviesRepository

    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {
        getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    }

    @Test
    fun `when invoke is called, then get movies from repository`() {
        val expectedMovies = MovieMock.movies
        every { repository.movies } returns flowOf(expectedMovies)

        val movies = runBlocking { getPopularMoviesUseCase().first() }

        assertEquals(expectedMovies, movies)
    }

    @Test
    fun `when invoke fails, then throws exception`() {
        val expectedException =
            AsyncException.UnknownError("Unknown error", IllegalStateException("Unknown error"))
        coEvery { repository.movies } throws expectedException

        var exception: Exception? = null
        try {
            getPopularMoviesUseCase()
        } catch (e: Exception) {
            exception = e
        }

        assertEquals(expectedException, exception)
    }
}