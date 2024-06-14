package com.dmoyahur.moviesapp.data.test.search

import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.model.error.AsyncException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class FakeSearchRepository(
    private val remoteMovies: List<MovieBo>? = emptyList(),
    localMovies: List<MovieBo> = emptyList(),
) : SearchRepository {

    override val previousSearches = MutableStateFlow(localMovies)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun findMovieSearchById(id: Int): Flow<MovieBo> {
        return previousSearches.flatMapLatest { movies ->
            flowOf(movies.find { it.id == id })
        }.onStart {
            try {
                val remoteMovie = remoteMovies?.find { it.id == id }
                remoteMovie?.let {
                    previousSearches.update { it.filterNot { movie -> movie.id == id } + remoteMovie }
                } ?: run {
                    throw AsyncException.UnknownError(
                        "Movie not found",
                        Throwable("Movie not found")
                    )
                }
            } catch (exception: Exception) {
                if (previousSearches.value.find { it.id == id } == null) throw exception
            }
        }.filterNotNull()
    }

    override suspend fun searchMovie(query: String): Flow<List<MovieBo>> {
        return withContext(Dispatchers.IO) {
            flow {
                remoteMovies?.filter { it.title.contains(query) }?.let { emit(it) }
                    ?: run {
                        throw AsyncException.ConnectionError("Connection error")
                    }
            }
        }
    }

    override suspend fun deleteMovieSearch(id: Int) {
        withContext(Dispatchers.IO) {
            previousSearches.update { it.filterNot { movie -> movie.id == id } }
        }
    }
}