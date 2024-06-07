package com.dmoyahur.moviesapp.data.repository.search

import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchLocalDataSource
import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchRemoteDataSource
import com.dmoyahur.moviesapp.testShared.MovieMock
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class SearchRepositoryTest {

    @Test
    fun `when previousSearches is called, then return previous searches from local data source`() {
        val expectedMovies = MovieMock.movies
        val localDataSource: SearchLocalDataSource = mockk {
            coEvery { previousSearches } returns flowOf(expectedMovies)
        }
        val remoteDataSource: SearchRemoteDataSource = mockk()
        val repository = SearchRepository(remoteDataSource, localDataSource)

        val movies = runBlocking { repository.previousSearches.first() }

        Assert.assertEquals(expectedMovies, movies)
    }

    @Test
    fun `when findMovieSearchById is called, then return movie from local data source`() {
        val expectedMovie = MovieMock.movies.first()
        val localDataSource: SearchLocalDataSource = mockk {
            coEvery { previousSearches } returns flowOf(MovieMock.movies)
            coEvery { findMovieSearchById(any()) } returns flowOf(expectedMovie)
            coJustRun { saveMovieSearch(any()) }
        }
        val remoteDataSource: SearchRemoteDataSource = mockk()
        val repository = SearchRepository(remoteDataSource, localDataSource)

        val movie = runBlocking { repository.findMovieSearchById(1).first() }

        Assert.assertEquals(expectedMovie, movie)
    }

    @Test
    fun `when searchMovie is called, then return movies from remote data source`() {
        val expectedMovies = listOf(MovieMock.movies.first(), MovieMock.movies.last())
        val localDataSource: SearchLocalDataSource = mockk {
            coEvery { previousSearches } returns flowOf(MovieMock.movies)
        }
        val remoteDataSource: SearchRemoteDataSource = mockk {
            coEvery { searchMovie(any()) } returns expectedMovies
        }
        val repository = SearchRepository(remoteDataSource, localDataSource)

        val movies = runBlocking { repository.searchMovie("Movie 1").first() }

        Assert.assertEquals(expectedMovies, movies)
    }
}