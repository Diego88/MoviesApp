package com.dmoyahur.moviesapp.data.remote

import retrofit2.http.GET

interface MoviesService {

    @GET("movie/popular")
    suspend fun fetchPopularMovies(): AsyncResult
}