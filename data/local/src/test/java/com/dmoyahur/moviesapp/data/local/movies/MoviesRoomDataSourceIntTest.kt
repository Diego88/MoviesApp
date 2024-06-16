package com.dmoyahur.moviesapp.data.local.movies

import android.os.Build
import com.dmoyahur.moviesapp.data.local.AppRoomDatabase
import com.dmoyahur.moviesapp.data.local.movies.dbo.MovieDbo
import com.dmoyahur.moviesapp.testShared.MovieMock
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
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
class MoviesRoomDataSourceIntTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppRoomDatabase

    @Inject
    @Named("test_moviesDao")
    lateinit var moviesDao: MoviesDao

    private lateinit var datasource: MoviesRoomDataSource

    @Before
    fun setUp() {
        hiltRule.inject()
        datasource = MoviesRoomDataSource(moviesDao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `when movies is called and database is empty, then return empty list`() =
        runTest {
            val actual = datasource.movies.first()

            Assert.assertEquals(emptyList<MovieDbo>(), actual)
        }

    @Test
    fun `when movies is called and database is not empty, then return movies`() =
        runTest {
            val expectedMovies = MovieMock.movies.sortedByDescending { it.popularity }
            datasource.saveMovies(expectedMovies)

            val actual = datasource.movies.first()

            Assert.assertEquals(expectedMovies, actual)
        }

    @Test
    fun `when saveMovies is called, then save movies in database`() = runTest {
        val expectedMovies = MovieMock.movies.sortedByDescending { it.popularity }

        val actualBeforeSave = datasource.movies.first()
        datasource.saveMovies(expectedMovies)
        val actualAfterSave = datasource.movies.first()

        Assert.assertEquals(emptyList<MovieDbo>(), actualBeforeSave)
        Assert.assertEquals(expectedMovies, actualAfterSave)
    }

    @Test
    fun `when findMovieById is called, then return movie`() = runTest {
        val movies = MovieMock.movies
        val expectedMovie = movies.first()
        datasource.saveMovies(movies)

        val actual = datasource.findMovieById(1).first()

        Assert.assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when findMovieById is called and movie is not in database, then return null`() = runTest {
        val movies = MovieMock.movies
        datasource.saveMovies(movies)

        val actual = datasource.findMovieById(0).first()

        Assert.assertNull(actual)
    }
}