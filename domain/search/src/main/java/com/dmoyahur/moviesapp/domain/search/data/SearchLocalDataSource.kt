package com.dmoyahur.moviesapp.domain.search.data

import com.dmoyahur.moviesapp.core.model.MovieBo
import kotlinx.coroutines.flow.Flow

interface SearchLocalDataSource {

    val previousSearches: Flow<List<MovieBo>>

    fun findMovieSearchById(id: Int): Flow<MovieBo?>

    suspend fun saveMovieSearch(movie: MovieBo)
}