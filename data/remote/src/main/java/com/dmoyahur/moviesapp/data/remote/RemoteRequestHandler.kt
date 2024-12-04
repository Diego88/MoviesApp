package com.dmoyahur.moviesapp.data.remote

import com.dmoyahur.moviesapp.model.error.AsyncException
import retrofit2.HttpException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

object RemoteRequestHandler {

    inline fun <T> request(function: () -> T): T {
        try {
            return function.invoke()
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Throwable) {
            throw processError(e)
        }
    }

    fun processError(error: Throwable): AsyncException {
        return when (error) {
            is HttpException, is UnknownHostException -> {
                AsyncException.ConnectionError(error.message ?: "Connection error")
            }
            else -> AsyncException.UnknownError(error.message ?: "Unknown error", error)
        }
    }
}