package com.dmoyahur.moviesapp.domain.movies.usecases

import com.dmoyahur.moviesapp.domain.movies.data.MoviesRepository
import com.dmoyahur.moviesapp.domain.movies.util.MovieMock
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FindMovieByIdUseCaseTest {

    @Test
    fun `when invoke is called, then return movie from repository`() {
        val expectedMovie = MovieMock.movies.first()
        val repository: MoviesRepository = mockk {
            every { findMovieById(any()) } returns flowOf(expectedMovie)
        }
        val useCase = FindMovieByIdUseCase(repository)

        val movies = runBlocking { useCase(1).first() }

        assertEquals(expectedMovie, movies)
    }
}