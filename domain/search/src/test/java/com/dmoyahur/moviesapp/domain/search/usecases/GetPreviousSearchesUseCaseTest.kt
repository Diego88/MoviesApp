package com.dmoyahur.moviesapp.domain.search.usecases

import com.dmoyahur.moviesapp.core.testing.MovieMock
import com.dmoyahur.moviesapp.domain.search.data.SearchRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetPreviousSearchesUseCaseTest {

    @Test
    fun `when invoke is called, then get previous searches from search repository`() {
        val expectedMovies = MovieMock.movies
        val repository: SearchRepository = mockk {
            every { previousSearches } returns flowOf(expectedMovies)
        }
        val useCase = GetPreviousSearchesUseCase(repository)

        val movies = runBlocking { useCase().first() }

        Assert.assertEquals(expectedMovies, movies)
    }
}