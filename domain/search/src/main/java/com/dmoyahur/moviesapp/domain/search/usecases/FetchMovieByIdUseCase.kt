package com.dmoyahur.moviesapp.domain.search.usecases

import com.dmoyahur.core.model.MovieBo
import com.dmoyahur.moviesapp.domain.search.data.SearchRepository
import kotlinx.coroutines.flow.Flow


class FetchMovieByIdUseCase(private val repository: SearchRepository) {

    operator fun invoke(id: Int): Flow<MovieBo> = repository.findMovieSearchById(id)
}