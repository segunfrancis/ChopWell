package com.project.segunfrancis.firebase.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.project.segunfrancis.domain.repository.FirebaseRepository
import com.project.segunfrancis.firebase.data.AuthManager
import com.project.segunfrancis.firebase.repository.FirebaseRepositoryImpl
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseDatabase.getInstance().also { it.setPersistenceEnabled(true) } }
    single { Firebase.auth }
    single<FirebaseRepository> { FirebaseRepositoryImpl(get(), get(), get()) }
}

val authModule = module {
    factory { AuthManager(get(), get()) }
}
