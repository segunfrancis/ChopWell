package com.project.segunfrancis.firebase.utils

sealed class SignUpResult<out T> {
    data class Success<T>(val data: T? = null): SignUpResult<T>()
    data class Error(val error: Throwable?): SignUpResult<Nothing>()
    object Loading: SignUpResult<Nothing>()
}
