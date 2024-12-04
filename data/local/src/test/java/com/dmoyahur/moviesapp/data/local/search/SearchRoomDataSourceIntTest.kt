package com.dmoyahur.moviesapp.data.local.search

import android.os.Build
import com.dmoyahur.moviesapp.data.local.AppRoomDatabase
import com.dmoyahur.moviesapp.data.local.movies.dbo.MovieDbo
import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.testShared.MovieMock
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
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
class SearchRoomDataSourceIntTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppRoomDatabase

    @Inject
    @Named("test_searchDao")
    lateinit var searchDao: SearchDao

    private lateinit var datasource: SearchRoomDataSource

    private val movies = MovieMock.movies

    @Before
    fun setUp() {
        hiltRule.inject()
        datasource = SearchRoomDataSource(searchDao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `when previousSearches is called and database is empty, then return empty list`() = runTest {
        val actual = datasource.previousSearches.first()

        Assert.assertEquals(emptyList<MovieDbo>(), actual)
    }

    @Test
    fun `when previousSearches is called and database is not empty, then return movies search`() = runTest {
        val expectedMovies = movies.take(2)
        saveMovies(expectedMovies)

        val actual = datasource.previousSearches.first().sorted()

        Assert.assertEquals(expectedMovies, actual)
    }

    @Test
    fun `when saveMovieSearch is called, then save movies search in database`() = runTest {
        val expectedMovies = movies.take(5)

        val actualBeforeSave = datasource.previousSearches.first()
        saveMovies(expectedMovies)
        val actualAfterSave = datasource.previousSearches.first().sorted()

        Assert.assertEquals(emptyList<MovieDbo>(), actualBeforeSave)
        Assert.assertEquals(expectedMovies, actualAfterSave)
    }

    @Test
    fun `when findMovieSearchById is called, then return movie`() = runTest {
        val expectedMovie = movies.first()
        saveMovies(movies)

        val actual = datasource.findMovieSearchById(1).first()

        Assert.assertEquals(expectedMovie, actual)
    }

    @Test
    fun `when findMovieSearchById is called and movie is not in database, then return null`() = runTest {
        saveMovies(movies)

        val actual = datasource.findMovieSearchById(0).first()

        Assert.assertNull(actual)
    }

    @Test
    fun `when getMovieSearchCount is called, then return the number of movies search in database`() = runTest {
        val movies = movies.take(5)
        saveMovies(movies)

        val actual = datasource.getMoviesSearchCount()

        Assert.assertEquals(5, actual)
    }

    @Test
    fun `when deleteMovieSearch is called, then delete movies search from database`() = runTest {
        saveMovies(movies.take(1))

        val actualBeforeDelete = datasource.getMoviesSearchCount()
        datasource.deleteMovieSearch(1)
        val actualAfterDelete = datasource.getMoviesSearchCount()

        Assert.assertEquals(1, actualBeforeDelete)
        Assert.assertEquals(0, actualAfterDelete)
    }

    @Test
    fun `when deleteOldestSearches is called, then delete oldest movies search from database`() = runTest {
        val excessCount = 2
        val expectedMoviesBeforeDelete = movies
        val expectedMoviesAfterDelete = movies.drop(excessCount)
        saveMovies(expectedMoviesBeforeDelete)

        val actualBeforeDelete = datasource.previousSearches.first().sorted()
        datasource.deleteOldestSearches(excessCount)
        val actualAfterDelete = datasource.previousSearches.first().sorted()

        Assert.assertEquals(expectedMoviesBeforeDelete, actualBeforeDelete)
        Assert.assertEquals(expectedMoviesAfterDelete, actualAfterDelete)
    }

    private suspend fun saveMovies(movies: List<MovieBo>) {
        withContext(Dispatchers.IO) {
            movies.forEach {
                datasource.saveMovieSearch(it)
            }
        }
    }

    // Order movies by id (we don't have timestamp here)
    private fun List<MovieBo>.sorted() = sortedBy { it.id }
}