package com.project.segunfrancis.firebase.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object Database {

    private var firebaseDatabase: FirebaseDatabase? = null

    fun init(): FirebaseDatabase {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance().also { it.setPersistenceEnabled(true) }
        }
        return firebaseDatabase!!
    }

    private var firebaseAuth: FirebaseAuth? = null

    fun initAuth(): FirebaseAuth {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance()
        }
        return firebaseAuth!!
    }

    fun getUserId(): String {
        return initAuth().currentUser?.uid.toString()
    }
}
