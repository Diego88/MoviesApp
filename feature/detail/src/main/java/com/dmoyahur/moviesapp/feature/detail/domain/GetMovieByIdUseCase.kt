package com.dmoyahur.moviesapp.feature.detail.domain

import com.dmoyahur.moviesapp.data.repository.movies.MoviesRepository
import com.dmoyahur.moviesapp.data.repository.search.SearchRepository
import com.dmoyahur.moviesapp.model.MovieBo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val searchRepository: SearchRepository
) {

    operator fun invoke(id: Int, fromSearch: Boolean): Flow<MovieBo> =
        if (fromSearch) {
            searchRepository.findMovieSearchById(id)
        } else {
            moviesRepository.findMovieById(id)
        }
}