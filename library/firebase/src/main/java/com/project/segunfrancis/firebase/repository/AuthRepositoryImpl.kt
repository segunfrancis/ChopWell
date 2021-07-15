package com.project.segunfrancis.firebase.repository

import android.content.Intent
import android.content.IntentSender
import com.project.segunfrancis.chopwell.data.AuthRepository
import com.project.segunfrancis.firebase.data.AuthManager
import com.project.segunfrancis.firebase.utils.SignInResult

class AuthRepositoryImpl(private val authManager: AuthManager) : AuthRepository {
    override fun beginSignInRequest() {
        authManager.beginSignInRequest()
    }

    override fun displaySignInUI(): IntentSender? {
        return authManager.displaySignInUI()
    }

    override fun oneTouchSignIn(intent: Intent): SignInResult {
        return authManager.oneTouchSignIn(intent)
    }
}
