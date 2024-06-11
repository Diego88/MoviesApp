package com.dmoyahur.moviesapp.data.local.search

import com.dmoyahur.moviesapp.data.local.LocalQueryHandler.query
import com.dmoyahur.moviesapp.data.local.search.mapper.MovieSearchDboMapper
import com.dmoyahur.moviesapp.data.repository.search.datasource.SearchLocalDataSource
import com.dmoyahur.moviesapp.model.MovieBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRoomDataSource @Inject constructor(
    private val searchDao: SearchDao
) : SearchLocalDataSource {

    override val previousSearches: Flow<List<MovieBo>> =
        query { searchDao.getMoviesSearch().map { MovieSearchDboMapper.mapToDomain(it) } }

    override fun findMovieSearchById(id: Int): Flow<MovieBo?> {
        return query {
            searchDao.findMovieSearchById(id)
                .map { movie -> movie?.let { MovieSearchDboMapper.mapToDomain(it) } }
        }
    }

    override suspend fun saveMovieSearch(movie: MovieBo) =
        query { searchDao.saveMovieSearch(MovieSearchDboMapper.mapToDatabase(movie)) }

    override suspend fun deleteMovieSearch(id: Int) {
        query { searchDao.deleteMovieSearch(id) }
    }

    override suspend fun getMoviesSearchCount(): Int = query { searchDao.getMoviesSearchCount() }

    override suspend fun deleteOldestSearches(excessCount: Int) {
        query { searchDao.deleteOldestSearches(excessCount) }
    }
}