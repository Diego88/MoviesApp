package com.dmoyahur.moviesapp.data

import retrofit2.http.GET

interface MoviesService {

    @GET("movie/popular")
    suspend fun fetchPopularMovies(): AsyncResult
}