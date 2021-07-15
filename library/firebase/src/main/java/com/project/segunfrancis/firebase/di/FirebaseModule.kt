package com.project.segunfrancis.firebase.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.project.segunfrancis.domain.repository.FirebaseRepository
import com.project.segunfrancis.firebase.data.AuthManager
import com.project.segunfrancis.firebase.repository.FirebaseRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseDatabase.getInstance().also { it.setPersistenceEnabled(true) } }
    single { FirebaseAuth.getInstance() }
    single<FirebaseRepository> { FirebaseRepositoryImpl(get(), get(), get()) }
}

val authModule = module {
    factory { AuthManager(androidContext(), get(), get()) }
}
