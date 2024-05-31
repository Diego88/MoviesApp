package com.dmoyahur.moviesapp.domain.movies.data

import com.dmoyahur.core.model.MovieBo
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {

    val movies: Flow<List<MovieBo>>

    fun findMovieById(id: Int): Flow<MovieBo?>

    suspend fun saveMovies(movies: List<MovieBo>)
}