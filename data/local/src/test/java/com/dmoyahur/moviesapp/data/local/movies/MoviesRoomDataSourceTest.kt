package com.dmoyahur.moviesapp.data.local.movies

import android.database.sqlite.SQLiteException
import com.dmoyahur.moviesapp.data.local.movies.mapper.MovieDboMapper
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
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

class MoviesRoomDataSourceTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @MockK
    lateinit var moviesDao: MoviesDao

    private lateinit var dataSource: MoviesRoomDataSource

    private val moviesDbo = MovieDboMock.moviesDbo

    @Before
    fun setUp() {
        every { moviesDao.getPopularMovies() } returns flowOf(moviesDbo)
        dataSource = MoviesRoomDataSource(moviesDao)
    }

    @Test
    fun `when movies is called, then return database movies`() {
        val expectedMovies = MovieDboMapper.mapToDomain(moviesDbo)

        val actual = runBlocking { dataSource.movies.first() }

        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when findMovieById is called, then return database movie`() {
        val movie = moviesDbo.first()
        val expectedMovie = MovieDboMapper.mapToDomain(movie)
        every { moviesDao.findMovieById(1) } returns flowOf(movie)

        val actual = runBlocking { dataSource.findMovieById(1).first() }

        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when saveMovies is called, then save movies in database`() {
        val movies = MovieMock.movies
        val moviesDbo = MovieDboMapper.mapToDatabase(movies)
        coJustRun { moviesDao.saveMovies(any()) }

        runBlocking { dataSource.saveMovies(movies) }

        coVerify { moviesDao.saveMovies(moviesDbo) }
    }

    @Test
    fun `when saveMovies fails, then throw exception`() {
        val movies = MovieMock.movies
        coEvery { moviesDao.saveMovies(any()) } throws SQLiteException("Database error")

        assertThrows("Database error", AsyncException.DatabaseError::class.java) {
            runBlocking { dataSource.saveMovies(movies) }
        }
    }
}