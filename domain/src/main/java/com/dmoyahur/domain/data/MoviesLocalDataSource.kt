package com.dmoyahur.domain.data

import com.dmoyahur.domain.model.MovieBo
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {

    val movies: Flow<List<MovieBo>>
    fun findMovieById(id: Int): Flow<MovieBo?>
    suspend fun save(movies: List<MovieBo>)
}