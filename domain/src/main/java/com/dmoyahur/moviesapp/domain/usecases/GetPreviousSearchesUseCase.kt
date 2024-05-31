package com.dmoyahur.moviesapp.domain.usecases

import com.dmoyahur.moviesapp.domain.data.MoviesRepository
import com.dmoyahur.moviesapp.domain.model.MovieBo
import kotlinx.coroutines.flow.Flow

class GetPreviousSearchesUseCase(private val repository: MoviesRepository) {

    operator fun invoke(): Flow<List<MovieBo>> = repository.previousSearches
}