package com.dmoyahur.moviesapp.feature.search.domain

import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import com.dmoyahur.moviesapp.testShared.MovieMock
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class FindMovieSearchByIdUseCaseTest {

    @Test
    fun `when invoke is called, then return movie from search repository`() {
        val expectedMovie = MovieMock.movies.first()
        val repository: SearchRepository = mockk {
            every { findMovieSearchById(any()) } returns flowOf(expectedMovie)
        }
        val useCase = FindMovieSearchByIdUseCase(repository)

        val movies = runBlocking { useCase(1).first() }

        Assert.assertEquals(expectedMovie, movies)
    }
}