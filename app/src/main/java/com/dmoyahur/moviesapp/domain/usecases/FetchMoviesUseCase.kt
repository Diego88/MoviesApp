package com.dmoyahur.moviesapp.domain.usecases

import com.dmoyahur.moviesapp.data.MoviesRepository
import com.dmoyahur.moviesapp.domain.MovieBo
import kotlinx.coroutines.flow.Flow

class FetchMoviesUseCase(private val repository: MoviesRepository) {
    operator fun invoke(): Flow<List<MovieBo>> = repository.movies
}