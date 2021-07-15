package com.project.segunfrancis.firebase.utils

sealed class SignInResult {
    data class IdToken(val token: String): SignInResult()
    data class Password(val username: String, val password: String): SignInResult()
    data class Error(val errorMessage: String?): SignInResult()
}
