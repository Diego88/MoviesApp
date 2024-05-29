package com.dmoyahur.domain.usecases

import com.dmoyahur.domain.data.MoviesRepository

class FindMovieByIdUseCase(private val repository: MoviesRepository) {
    operator fun invoke(id: Int) = repository.findMovieById(id)
}