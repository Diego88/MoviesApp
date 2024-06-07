package com.dmoyahur.moviesapp.data.repository.movies

import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesLocalDataSource
import com.dmoyahur.moviesapp.data.repository.movies.datasource.MoviesRemoteDataSource
import com.dmoyahur.moviesapp.testShared.MovieMock
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesRepositoryTest {

    @Test
    fun `when movies is called, then return movies from local data source`() {
        val expectedMovies = MovieMock.movies
        val localDataSource: MoviesLocalDataSource = mockk {
            coEvery { movies } returns flowOf(expectedMovies)
        }
        val remoteDataSource: MoviesRemoteDataSource = mockk()
        val moviesRepository = MoviesRepository(remoteDataSource, localDataSource)

        val movies = runBlocking { moviesRepository.movies.first() }

        assertEquals(expectedMovies, movies)
    }

    @Test
    fun `when findMovieById is called, then return movie from local data source`() {
        val expectedMovie = MovieMock.movies.first()
        val localDataSource: MoviesLocalDataSource = mockk {
            coEvery { movies } returns flowOf(MovieMock.movies)
            coEvery { findMovieById(any()) } returns flowOf(expectedMovie)
        }
        val remoteDataSource: MoviesRemoteDataSource = mockk()
        val moviesRepository = MoviesRepository(remoteDataSource, localDataSource)

        val movie = runBlocking { moviesRepository.findMovieById(1).first() }

        assertEquals(expectedMovie, movie)
    }
}