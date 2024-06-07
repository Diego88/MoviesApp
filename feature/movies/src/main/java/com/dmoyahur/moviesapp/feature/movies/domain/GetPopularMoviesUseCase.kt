package com.dmoyahur.moviesapp.feature.movies.domain

import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.data.repository.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(): Flow<List<MovieBo>> = repository.movies
}