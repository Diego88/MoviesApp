package com.dmoyahur.moviesapp.data.repository.search.datasource

import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.model.error.AsyncException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FakeSearchRemoteDataSource(
    private val moviesSearch: List<MovieBo> = emptyList(),
) : SearchRemoteDataSource {
    override suspend fun fetchMovieById(id: Int): MovieBo = withContext(Dispatchers.IO) {
        moviesSearch.find { it.id == id } ?: run {
            throw AsyncException.UnknownError(
                "Movie not found",
                Exception("Movie not found")
            )
        }
    }

    override suspend fun searchMovie(query: String): List<MovieBo> = withContext(Dispatchers.IO) {
        moviesSearch.filter { it.title.contains(query) }
    }
}