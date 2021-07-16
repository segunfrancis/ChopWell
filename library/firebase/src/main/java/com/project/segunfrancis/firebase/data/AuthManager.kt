package com.project.segunfrancis.firebase.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.project.segunfrancis.firebase.model.User
import timber.log.Timber

class AuthManager(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
) {
    fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun anonymousSignIn(response: (FirebaseUser?) -> Unit) {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result?.user
                user?.let {
                    val model = User(it.uid, it.email)
                    database.reference.child("users").child(it.uid)
                        .setValue(model)
                }
                response(user)
            } else {
                Timber.e(task.exception)
                response(null)
            }
        }
    }
}
