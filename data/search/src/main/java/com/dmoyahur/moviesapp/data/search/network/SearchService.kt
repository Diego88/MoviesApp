package com.dmoyahur.moviesapp.data.search.network

import com.dmoyahur.moviesapp.core.data.network.model.AsyncResult
import com.dmoyahur.moviesapp.core.data.network.model.MovieDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {

    @GET("movie/{id}")
    suspend fun fetchMovieById(@Path("id") id: Int): MovieDto

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): AsyncResult
}