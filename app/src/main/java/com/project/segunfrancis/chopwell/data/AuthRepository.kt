package com.project.segunfrancis.chopwell.data

import android.content.Intent
import android.content.IntentSender
import com.project.segunfrancis.firebase.utils.SignInResult

interface AuthRepository {
    fun beginSignInRequest()
    fun displaySignInUI(): IntentSender?
    fun oneTouchSignIn(intent: Intent): SignInResult
}