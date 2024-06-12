package com.dmoyahur.moviesapp.data.local.search

import android.database.sqlite.SQLiteException
import com.dmoyahur.moviesapp.data.local.search.dbo.MovieSearchDbo
import com.dmoyahur.moviesapp.data.local.search.mapper.MovieSearchDboMapper
import com.dmoyahur.moviesapp.model.error.AsyncException
import com.dmoyahur.moviesapp.testShared.MovieMock
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchRoomDataSourceTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @MockK
    lateinit var searchDao: SearchDao

    private lateinit var dataSource: SearchRoomDataSource

    private val moviesSearchDbo = MovieSearchDboMock.moviesSearchDbo

    @Before
    fun setUp() {
        every { searchDao.getMoviesSearch() } returns flowOf(moviesSearchDbo)
        dataSource = SearchRoomDataSource(searchDao)
    }

    @Test
    fun `when previousSearches is called, then return database movies search mapped`() {
        val expectedMovies = MovieSearchDboMapper.mapToDomain(moviesSearchDbo)

        val actual = runBlocking { dataSource.previousSearches.first() }

        assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when findMovieSearchById is called, then return database movie search mapped`() {
        val movie = moviesSearchDbo.first()
        val expectedMovie = MovieSearchDboMapper.mapToDomain(movie)
        every { searchDao.findMovieSearchById(1) } returns flowOf(movie)

        val actual = runBlocking { dataSource.findMovieSearchById(1).first() }

        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when saveMovieSearch is called, then save movie search in database`() {
        val movie = MovieMock.movies.first()
        val movieDbo = MovieSearchDboMapper.mapToDatabase(movie)
        val slot = slot<MovieSearchDbo>()
        coJustRun { searchDao.saveMovieSearch(any()) }

        runBlocking { dataSource.saveMovieSearch(movie) }

        coVerify { searchDao.saveMovieSearch(capture(slot)) }
        val expectedMovie = movieDbo.copy(timeStamp = slot.captured.timeStamp)
        assertEquals(expectedMovie, slot.captured)
    }

    @Test
    fun `when saveMovieSearch fails, then throw exception`() {
        val movie = MovieMock.movies.first()
        coEvery { searchDao.saveMovieSearch(any()) } throws SQLiteException("Database error")

        assertThrows("Database error", AsyncException.DatabaseError::class.java) {
            runBlocking { dataSource.saveMovieSearch(movie) }
        }
    }

    @Test
    fun `when deleteMovieSearch is called, then delete movie search from database`() {
        coJustRun { searchDao.deleteMovieSearch(any()) }

        runBlocking { dataSource.deleteMovieSearch(1) }

        coVerify { searchDao.deleteMovieSearch(1) }
    }

    @Test
    fun `when getMoviesSearchCount, then return number of movies search in database`() {
        val expectedCount = 12
        coEvery { searchDao.getMoviesSearchCount() } returns expectedCount

        val actual = runBlocking { dataSource.getMoviesSearchCount() }

        assertEquals(expectedCount, actual)
    }

    @Test
    fun `when deleteOldestSearches is called, then delete oldest movies search from database`() {
        coJustRun { searchDao.deleteOldestSearches(any()) }

        runBlocking { dataSource.deleteOldestSearches(1) }

        coVerify { searchDao.deleteOldestSearches(1) }
    }

    @Test
    fun `when deleteOldestSearches fails, then throw exception`() {
        coEvery { searchDao.deleteOldestSearches(any()) } throws Exception("Unknown error")

        assertThrows("Unknown error", AsyncException.DatabaseError::class.java) {
            runBlocking { dataSource.deleteOldestSearches(1) }
        }
    }
}