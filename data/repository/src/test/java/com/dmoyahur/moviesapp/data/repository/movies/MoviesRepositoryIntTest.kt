package com.dmoyahur.moviesapp.data.repository.movies

import com.dmoyahur.moviesapp.data.repository.movies.datasource.FakeMoviesLocalDataSource
import com.dmoyahur.moviesapp.data.repository.movies.datasource.FakeMoviesRemoteDataSource
import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.testShared.MovieMock
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesRepositoryIntTest {

    private lateinit var repository: MoviesRepository

    private val movies = MovieMock.movies

    @Test
    fun `when movies is called, then return movies from local datasource`() {
        val expectedMovies = movies
        initRepository(localMovies = movies)

        val actual = runBlocking { repository.movies.first() }

        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when movies is called, then return updated movies`() {
        val expectedMovies = movies
        initRepository(remoteMovies = movies, localMovies = movies.take(4))

        val actual = runBlocking { repository.movies.first() }

        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when findMovieById is called, then return movie from local data source`() {
        val expectedMovie = movies.first()
        initRepository(localMovies = movies)

        val actual = runBlocking { repository.findMovieById(1).first() }

        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when findMovieById is called and result is null, then repository does not emit result`() {
        initRepository(localMovies = movies.take(5))

        val actual = runBlocking { repository.findMovieById(6).count() }

        assertEquals(0, actual)
    }

    private fun initRepository(
        remoteMovies: List<MovieBo> = emptyList(),
        localMovies: List<MovieBo> = emptyList()
    ) {
        val remoteDataSource = FakeMoviesRemoteDataSource(remoteMovies)
        val localDataSource = FakeMoviesLocalDataSource(localMovies)
        repository = MoviesRepositoryImpl(remoteDataSource, localDataSource)
    }
}