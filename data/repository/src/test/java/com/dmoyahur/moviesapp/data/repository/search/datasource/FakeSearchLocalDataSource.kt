package com.dmoyahur.moviesapp.data.repository.search.datasource

import com.dmoyahur.moviesapp.model.MovieBo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class FakeSearchLocalDataSource(
    moviesSearch: List<MovieBo> = emptyList()
) : SearchLocalDataSource {

    private val inMemoryMoviesSearch = MutableStateFlow(moviesSearch)

    override val previousSearches: Flow<List<MovieBo>> = inMemoryMoviesSearch

    override fun findMovieSearchById(id: Int): Flow<MovieBo?> = inMemoryMoviesSearch.flatMapLatest {
        flowOf(it.find { movie -> movie.id == id })
    }

    override suspend fun saveMovieSearch(movie: MovieBo) {
        withContext(Dispatchers.IO) {
            val updatedMovies = inMemoryMoviesSearch.value.filterNot { it.id == movie.id } + movie
            inMemoryMoviesSearch.update { updatedMovies }
        }
    }

    override suspend fun deleteMovieSearch(id: Int) {
        withContext(Dispatchers.IO) {
            inMemoryMoviesSearch.update { currentMovies ->
                currentMovies.filterNot { it.id == id }
            }
        }
    }

    override suspend fun getMoviesSearchCount(): Int = withContext(Dispatchers.IO) {
        inMemoryMoviesSearch.value.size
    }

    override suspend fun deleteOldestSearches(excessCount: Int) {
        withContext(Dispatchers.IO) {
            inMemoryMoviesSearch.update { currentMovies ->
                currentMovies.drop(excessCount)
            }
        }
    }
}