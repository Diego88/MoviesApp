package com.dmoyahur.moviesapp.data.local.movies

import android.os.Build
import com.dmoyahur.moviesapp.data.local.AppRoomDatabase
import com.dmoyahur.moviesapp.data.local.movies.dbo.MovieDbo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    application = HiltTestApplication::class,
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.UPSIDE_DOWN_CAKE]
)
class MoviesDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppRoomDatabase

    @Inject
    @Named("test_moviesDao")
    lateinit var moviesDao: MoviesDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `when getPopularMovies is called and database is empty, then return empty list`() =
        runTest {
            val actual = moviesDao.getPopularMovies().first()

            assertEquals(emptyList<MovieDbo>(), actual)
        }

    @Test
    fun `when getPopularMovies is called and database is not empty, then return movies`() =
        runTest {
            val expectedMovies = MovieDboMock.moviesDbo
            moviesDao.saveMovies(expectedMovies)

            val actual = moviesDao.getPopularMovies().first()

            assertEquals(expectedMovies, actual)
        }

    @Test
    fun `when saveMovies is called, then save movies in database`() = runTest {
        val expectedMovies = MovieDboMock.moviesDbo

        val actualBeforeSave = moviesDao.getPopularMovies().first()
        moviesDao.saveMovies(expectedMovies)
        val actualAfterSave = moviesDao.getPopularMovies().first()

        assertEquals(emptyList<MovieDbo>(), actualBeforeSave)
        assertEquals(expectedMovies, actualAfterSave)
    }

    @Test
    fun `when findMovieById is called, then return movie`() = runTest {
        val movies = MovieDboMock.moviesDbo
        val expectedMovie = movies.first()
        moviesDao.saveMovies(movies)

        val actual = moviesDao.findMovieById(1).first()

        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when findMovieById is called and movie is not in database, then return null`() = runTest {
        val movies = MovieDboMock.moviesDbo
        moviesDao.saveMovies(movies)

        val actual = moviesDao.findMovieById(0).first()

        assertNull(actual)
    }
}