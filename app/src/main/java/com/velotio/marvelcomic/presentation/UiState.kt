package com.velotio.marvelcomic.presentation

sealed class UiState<T>(open val data: T?) {
    data class Loading<T>(override val data: T? = null) : UiState<T>(data)
    data class Loaded<T>(override val data: T? = null) : UiState<T>(data)
    data class NoInternetError<T>(val error: Throwable, override val data: T? = null) : UiState<T>(data)
    data class ApiError<T>(val error: Throwable, override val data: T? = null) : UiState<T>(data)
}