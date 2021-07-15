package com.project.segunfrancis.chopwell.presentation.utils

sealed class Result<out T> {
    data class Success<T>(val data: T? = null) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()
    object Loading: Result<Nothing>()
}
