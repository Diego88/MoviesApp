package com.dmoyahur.moviesapp.domain.movies.usecases

import com.dmoyahur.core.model.MovieBo
import com.dmoyahur.moviesapp.domain.movies.data.MoviesRepository
import kotlinx.coroutines.flow.Flow

class FetchMoviesUseCase(private val repository: MoviesRepository) {

    operator fun invoke(): Flow<List<MovieBo>> = repository.movies
}