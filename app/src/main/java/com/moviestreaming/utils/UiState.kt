package com.moviestreaming.utils
sealed class UiState<T> {
    data class Success<T>(val data: T) : UiState<T>()
    data class Error<T>(val message: String, val cause: Throwable? = null, val data: T? = null) : UiState<T>()
    class Loading<T> : UiState<T>()
}


