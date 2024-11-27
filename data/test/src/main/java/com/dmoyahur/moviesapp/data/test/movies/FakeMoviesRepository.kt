package com.dmoyahur.moviesapp.data.test.movies

import com.dmoyahur.moviesapp.data.repository.movies.MoviesRepository
import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.model.error.AsyncException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class FakeMoviesRepository(
    private val remoteMovies: List<MovieBo>? = emptyList(),
    localMovies: List<MovieBo> = emptyList()
) : MoviesRepository {

    private var localDataSourceMovies = MutableStateFlow(localMovies)

    override val movies = localDataSourceMovies.onStart {
        remoteMovies?.let {
            localDataSourceMovies.update { currentMovies ->
                currentMovies.filterNot { movie -> movie.id in remoteMovies.map { it.id } } + remoteMovies
            }
        } ?: run {
            if (localDataSourceMovies.value.isEmpty()) {
                throw AsyncException.ConnectionError("Connection error")
            }
        }
    }

    override fun findMovieById(id: Int): Flow<MovieBo> =
        flowOf(
            localDataSourceMovies.value.find { it.id == id }
        ).filterNotNull()
}