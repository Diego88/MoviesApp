package com.dmoyahur.moviesapp.domain.usecases

import com.dmoyahur.moviesapp.domain.data.MoviesRepository
import com.dmoyahur.moviesapp.domain.model.MovieBo
import kotlinx.coroutines.flow.Flow

class SearchMovieUseCase(private val repository: MoviesRepository) {

    suspend operator fun invoke(query: String): Flow<List<MovieBo>> = repository.searchMovie(query)
}