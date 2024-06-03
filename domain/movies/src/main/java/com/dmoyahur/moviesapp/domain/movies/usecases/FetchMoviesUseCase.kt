package com.dmoyahur.moviesapp.domain.movies.usecases

import com.dmoyahur.moviesapp.core.model.MovieBo
import com.dmoyahur.moviesapp.domain.movies.data.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(): Flow<List<MovieBo>> = repository.movies
}