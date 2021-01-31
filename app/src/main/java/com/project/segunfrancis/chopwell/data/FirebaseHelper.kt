package com.project.segunfrancis.chopwell.data

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.database.ValueEventListener

interface FirebaseHelper {
    fun googleSignIn(): GoogleSignInClient
    fun isUserAdmin(): Boolean
    fun getMeals(): ValueEventListener
    fun addFavorite()
    fun removeFavorite()
    fun getFavorites()
    fun enableOfflinePersistence()
}