package com.project.segunfrancis.firebase.di

import com.project.segunfrancis.chopwell.data.AuthRepository
import com.project.segunfrancis.domain.repository.FirebaseRepository
import com.project.segunfrancis.firebase.data.AuthManager
import com.project.segunfrancis.firebase.repository.FirebaseRepositoryImpl
import com.project.segunfrancis.firebase.data.Database
import com.project.segunfrancis.firebase.repository.AuthRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseModule = module {
    single<FirebaseRepository> { FirebaseRepositoryImpl(Database.init(), get(), Database.getUserId()) }
}

val authModule = module {
    factory { AuthManager(androidContext()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}
