package com.dmoyahur.moviesapp.data.repository.movies.datasource

import com.dmoyahur.moviesapp.model.MovieBo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class FakeMoviesLocalDataSource(movies: List<MovieBo> = emptyList()) : MoviesLocalDataSource {

    private val inMemoryMovies = MutableStateFlow(movies)

    override val movies: Flow<List<MovieBo>> = inMemoryMovies

    override fun findMovieById(id: Int): Flow<MovieBo?> {
        return flowOf(inMemoryMovies.value.find { movie -> movie.id == id })
    }

    override suspend fun saveMovies(movies: List<MovieBo>) {
        withContext(Dispatchers.IO) {
            inMemoryMovies.update {
                movies + inMemoryMovies.value.filterNot { it in movies }
            }
        }
    }
}