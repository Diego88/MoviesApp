package com.dmoyahur.moviesapp.data.local.search

import android.os.Build
import com.dmoyahur.moviesapp.data.local.AppRoomDatabase
import com.dmoyahur.moviesapp.data.local.movies.dbo.MovieDbo
import com.dmoyahur.moviesapp.data.local.search.dbo.MovieSearchDbo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
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
class SearchDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppRoomDatabase

    @Inject
    @Named("test_searchDao")
    lateinit var searchDao: SearchDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `when getMoviesSearch is called and database is empty, then return empty list`() =
        runTest {
            val actual = searchDao.getMoviesSearch().first()

            assertEquals(emptyList<MovieDbo>(), actual)
        }

    @Test
    fun `when getMoviesSearch is called and database is not empty, then return movies search`() =
        runTest {
            val movies = MovieSearchDboMock.moviesSearchDbo
            val expectedMovies = movies.take(2)
            saveMovies(expectedMovies)

            val actual = searchDao.getMoviesSearch().first()

            assertEquals(expectedMovies, actual)
        }

    @Test
    fun `when saveMovieSearch is called, then save movies search in database`() = runTest {
        val movies = MovieSearchDboMock.moviesSearchDbo
        val expectedMovies = movies.take(5)

        val actualBeforeSave = searchDao.getMoviesSearch().first()
        saveMovies(expectedMovies)
        val actualAfterSave = searchDao.getMoviesSearch().first()

        assertEquals(emptyList<MovieDbo>(), actualBeforeSave)
        assertEquals(expectedMovies, actualAfterSave)
    }

    @Test
    fun `when findMovieSearchById is called, then return movie`() = runTest {
        val movies = MovieSearchDboMock.moviesSearchDbo
        val expectedMovie = movies.first()
        saveMovies(movies)

        val actual = searchDao.findMovieSearchById(1).first()

        assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when findMovieSearchById is called and movie is not in database, then return null`() =
        runTest {
            val movies = MovieSearchDboMock.moviesSearchDbo
            saveMovies(movies)

            val actual = searchDao.findMovieSearchById(0).first()

            assertNull(actual)
        }

    @Test
    fun `when getMovieSearchCount is called, then return the number of movies search in database`() =
        runTest {
            val movies = MovieSearchDboMock.moviesSearchDbo.take(5)
            saveMovies(movies)

            val actual = searchDao.getMoviesSearchCount()

            assertEquals(5, actual)
        }

    @Test
    fun `when deleteMovieSearch is called, then delete movies search from database`() =
        runTest {
            val movies = MovieSearchDboMock.moviesSearchDbo
            saveMovies(movies.take(1))

            val actualBeforeDelete = searchDao.getMoviesSearchCount()
            searchDao.deleteMovieSearch(movies.first().id)
            val actualAfterDelete = searchDao.getMoviesSearchCount()

            assertEquals(1, actualBeforeDelete)
            assertEquals(0, actualAfterDelete)
        }

    @Test
    fun `when deleteOldestSearches is called, then delete oldest movies search from database`() =
        runTest {
            val excessCount = 2
            val expectedMoviesBeforeDelete = MovieSearchDboMock.moviesSearchDbo
            val expectedMoviesAfterDelete =
                expectedMoviesBeforeDelete.sortedBy { it.timeStamp }.drop(excessCount)
            saveMovies(expectedMoviesBeforeDelete)

            val actualBeforeDelete = searchDao.getMoviesSearch().first()
            searchDao.deleteOldestSearches(excessCount)
            val actualAfterDelete = searchDao.getMoviesSearch().first()

            assertEquals(expectedMoviesBeforeDelete, actualBeforeDelete)
            assertEquals(expectedMoviesAfterDelete, actualAfterDelete)
        }

    private suspend fun saveMovies(movies: List<MovieSearchDbo>) {
        withContext(Dispatchers.IO) {
            movies.forEach {
                searchDao.saveMovieSearch(it)
            }
        }
    }
}