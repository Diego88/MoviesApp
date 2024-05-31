package com.dmoyahur.moviesapp.data.network

import com.dmoyahur.moviesapp.data.network.model.AsyncResult
import com.dmoyahur.moviesapp.data.network.model.MovieDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    suspend fun fetchPopularMovies(): AsyncResult

    @GET("movie/{id}")
    suspend fun fetchMovieById(@Path("id") id: Int): MovieDto

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): AsyncResult
}