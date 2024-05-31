package com.dmoyahur.moviesapp.data.movies.network

import com.dmoyahur.moviesapp.core.data.network.model.AsyncResult
import retrofit2.http.GET

interface MoviesService {

    @GET("movie/popular")
    suspend fun fetchPopularMovies(): AsyncResult
}