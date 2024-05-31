package com.dmoyahur.moviesapp.domain.data

import com.dmoyahur.moviesapp.domain.model.MovieBo
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {

    val movies: Flow<List<MovieBo>>
    val previousSearches: Flow<List<MovieBo>>

    fun findMovieById(id: Int): Flow<MovieBo?>

    fun findMovieSearchById(id: Int): Flow<MovieBo?>

    suspend fun saveMovies(movies: List<MovieBo>)

    suspend fun saveMovieSearch(movie: MovieBo)
}