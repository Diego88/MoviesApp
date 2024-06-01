package com.dmoyahur.moviesapp.domain.movies.usecases

import com.dmoyahur.core.model.MovieBo
import com.dmoyahur.moviesapp.domain.movies.data.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindMovieByIdUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(id: Int): Flow<MovieBo> = repository.findMovieById(id)
}