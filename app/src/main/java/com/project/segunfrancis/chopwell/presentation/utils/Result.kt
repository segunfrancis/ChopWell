package com.project.segunfrancis.chopwell.presentation.utils

import com.google.firebase.database.DatabaseError

sealed class Result<out T> {
    data class Success<T>(val data: T? = null) : Result<T>()
    data class Error(val error: DatabaseError) : Result<Nothing>()
}
