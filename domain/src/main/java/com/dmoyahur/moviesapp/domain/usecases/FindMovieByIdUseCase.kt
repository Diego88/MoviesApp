package com.dmoyahur.moviesapp.domain.usecases

import com.dmoyahur.moviesapp.domain.data.MoviesRepository
import com.dmoyahur.moviesapp.domain.model.MovieBo
import kotlinx.coroutines.flow.Flow

class FindMovieByIdUseCase(private val repository: MoviesRepository) {

    operator fun invoke(id: Int, fromSearch: Boolean): Flow<MovieBo> =
        if (fromSearch) repository.findMovieSearchById(id) else repository.findMovieById(id)
}