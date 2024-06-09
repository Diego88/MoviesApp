package com.dmoyahur.moviesapp.model.error

sealed class AsyncException(open val debugMessage: String) : Exception() {
    data class ConnectionError(override val debugMessage: String) : AsyncException(debugMessage)
    data class DatabaseError(override val debugMessage: String) : AsyncException(debugMessage)
    data class UnknownError(override val debugMessage: String, val error: Throwable) :
        AsyncException(debugMessage)
}