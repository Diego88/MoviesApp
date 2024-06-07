package com.dmoyahur.moviesapp.feature.movies.domain

import com.dmoyahur.moviesapp.data.repository.movies.MoviesRepository
import com.dmoyahur.moviesapp.testShared.MovieMock
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPopularMoviesUseCaseTest {

    @Test
    fun `when invoke is called, then get movies from repository`() {
        val expectedMovies = MovieMock.movies
        val repository: MoviesRepository = mockk {
            every { movies } returns flowOf(expectedMovies)
        }
        val useCase = GetPopularMoviesUseCase(repository)

        val movies = runBlocking { useCase().first() }

        assertEquals(expectedMovies, movies)
    }
}