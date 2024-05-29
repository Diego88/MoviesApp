package com.dmoyahur.moviesapp.domain.usecases

import com.dmoyahur.moviesapp.data.MoviesRepository

class FindMovieByIdUseCase(private val repository: MoviesRepository) {
    operator fun invoke(id: Int) = repository.findMovieById(id)
}