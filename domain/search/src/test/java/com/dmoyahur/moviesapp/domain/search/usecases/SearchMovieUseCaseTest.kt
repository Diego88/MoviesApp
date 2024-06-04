package com.dmoyahur.moviesapp.domain.search.usecases

import com.dmoyahur.moviesapp.domain.search.data.SearchRepository
import com.dmoyahur.moviesapp.domain.search.util.MovieMock
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class SearchMovieUseCaseTest {

    @Test
    fun `when invoke is called, then return movies from search repository`() {
        val expectedMovies = listOf(MovieMock.movies.first(), MovieMock.movies.last())
        val repository: SearchRepository = mockk {
            coEvery { searchMovie(any()) } returns flowOf(expectedMovies)
        }
        val useCase = SearchMovieUseCase(repository)

        val movies = runBlocking { useCase("Movie 1").first() }

        Assert.assertEquals(expectedMovies, movies)
    }
}