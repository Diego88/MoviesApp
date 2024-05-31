package com.dmoyahur.domain.detail

import com.dmoyahur.core.model.MovieBo
import com.dmoyahur.moviesapp.domain.movies.usecases.FindMovieByIdUseCase
import com.dmoyahur.moviesapp.domain.search.usecases.FetchMovieByIdUseCase
import kotlinx.coroutines.flow.Flow

class GetMovieByIdUseCase(
    private val findMovieByIdUseCase: FindMovieByIdUseCase,
    private val fetchMovieByIdUseCase: FetchMovieByIdUseCase
) {

    operator fun invoke(id: Int, fromSearch: Boolean): Flow<MovieBo> =
        if (fromSearch) fetchMovieByIdUseCase(id) else findMovieByIdUseCase(id)
}