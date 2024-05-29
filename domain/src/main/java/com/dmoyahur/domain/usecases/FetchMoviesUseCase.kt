package com.dmoyahur.domain.usecases

import com.dmoyahur.domain.data.MoviesRepository
import com.dmoyahur.domain.model.MovieBo
import kotlinx.coroutines.flow.Flow

class FetchMoviesUseCase(private val repository: MoviesRepository) {
    operator fun invoke(): Flow<List<MovieBo>> = repository.movies
}