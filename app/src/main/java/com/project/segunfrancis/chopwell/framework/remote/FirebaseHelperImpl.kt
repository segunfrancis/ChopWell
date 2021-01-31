package com.project.segunfrancis.chopwell.framework.remote

import android.content.Context
import android.content.SharedPreferences
import com.project.segunfrancis.chopwell.data.FirebaseHelper
import com.project.segunfrancis.chopwell.presentation.utils.AppConstants.MEALS
import com.project.segunfrancis.chopwell.presentation.utils.AppConstants.SHARED_PREF_NAME
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseHelperImpl(
        private val gso: GoogleSignInOptions,
        private val database: FirebaseDatabase,
        private val firebaseAuth: FirebaseAuth,
        private val valueEventListener: ValueEventListener,
        private val context: Context
) : FirebaseHelper {

    private val preference: SharedPreferences by lazy { context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE) }

    override fun googleSignIn(): GoogleSignInClient {
        return GoogleSignIn.getClient(context, gso)
    }

    override fun isUserAdmin(): Boolean {
        return firebaseAuth.currentUser?.displayName.equals("Segun Francis")
    }

    override fun getMeals(): ValueEventListener {
        return database.getReference(MEALS).addValueEventListener(valueEventListener)
    }

    override fun addFavorite() {
        TODO("Not yet implemented")
    }

    override fun removeFavorite() {
        TODO("Not yet implemented")
    }

    override fun getFavorites() {
        TODO("Not yet implemented")
    }

    override fun enableOfflinePersistence() {
        database.setPersistenceEnabled(true)
    }

}