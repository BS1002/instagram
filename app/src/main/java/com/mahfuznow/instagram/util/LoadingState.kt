package com.mahfuznow.instagram.util

sealed class LoadingState<T> {
    class Loading<T> : LoadingState<T>()
    data class Success<T>(val data: T) : LoadingState<T>()
    data class Error<T>(val e: Throwable) : LoadingState<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success<T>(data)
        fun <T> error(e: Throwable) = Error<T>(e)
    }
}