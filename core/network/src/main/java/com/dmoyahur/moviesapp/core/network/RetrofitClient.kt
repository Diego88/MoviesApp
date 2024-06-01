package com.dmoyahur.moviesapp.core.network

import com.dmoyahur.moviesapp.data.movies.network.MoviesService
import com.dmoyahur.moviesapp.data.search.network.SearchService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

internal class RetrofitClient {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(::apiKeyAsQuery)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val moviesInstance = buildRetrofitInstance().create<MoviesService>()

    val searchInstance = buildRetrofitInstance().create<SearchService>()

    private fun apiKeyAsQuery(chain: Interceptor.Chain) = chain.proceed(
        chain.request()
            .newBuilder()
            .url(
                chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()
            )
            .build()
    )

    private fun buildRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}