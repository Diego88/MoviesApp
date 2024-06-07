package com.dmoyahur.moviesapp.feature.search.domain

import com.dmoyahur.moviesapp.model.MovieBo
import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindMovieSearchByIdUseCase @Inject constructor(private val repository: SearchRepository) {

    operator fun invoke(id: Int): Flow<MovieBo> = repository.findMovieSearchById(id)
}