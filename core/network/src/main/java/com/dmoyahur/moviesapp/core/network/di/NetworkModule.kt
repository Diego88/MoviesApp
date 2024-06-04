package com.dmoyahur.moviesapp.core.network.di

import com.dmoyahur.moviesapp.core.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(): String = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideJsonConverter(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideInterceptor(@Named("apiKey") apiKey: String): Interceptor = Interceptor { chain ->
        chain.proceed(
            chain.request()
                .newBuilder()
                .url(
                    chain.request()
                        .url
                        .newBuilder()
                        .addQueryParameter("api_key", apiKey)
                        .build()
                )
                .build()
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofitClient(
        @Named("baseUrl") baseUrl: String,
        okHttpClient: OkHttpClient,
        jsonConverter: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(jsonConverter.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}