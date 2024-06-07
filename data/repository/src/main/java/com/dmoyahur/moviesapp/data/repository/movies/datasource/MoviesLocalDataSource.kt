package com.dmoyahur.moviesapp.data.repository.movies.datasource

import com.dmoyahur.moviesapp.model.MovieBo
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {

    val movies: Flow<List<MovieBo>>

    fun findMovieById(id: Int): Flow<MovieBo?>

    suspend fun saveMovies(movies: List<MovieBo>)
}