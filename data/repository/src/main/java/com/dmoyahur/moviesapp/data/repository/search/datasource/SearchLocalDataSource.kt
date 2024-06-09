package com.dmoyahur.moviesapp.data.repository.search.datasource

import com.dmoyahur.moviesapp.model.MovieBo
import kotlinx.coroutines.flow.Flow

interface SearchLocalDataSource {

    val previousSearches: Flow<List<MovieBo>>

    fun findMovieSearchById(id: Int): Flow<MovieBo?>

    fun findMovieSearchByQuery(query: String): Flow<List<MovieBo>>

    suspend fun saveMovieSearch(movie: MovieBo)
}