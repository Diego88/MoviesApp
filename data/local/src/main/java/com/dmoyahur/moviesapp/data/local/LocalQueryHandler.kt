package com.dmoyahur.moviesapp.data.local

import com.dmoyahur.moviesapp.model.error.AsyncException
import kotlin.coroutines.cancellation.CancellationException

object LocalQueryHandler {

    inline fun <T> query(block: () -> T): T {
        return try {
            block()
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (e: Throwable) {
            throw AsyncException.DatabaseError(e.message ?: "Database error")
        }
    }
}