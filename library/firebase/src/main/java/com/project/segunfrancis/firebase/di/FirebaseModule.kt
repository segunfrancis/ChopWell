package com.project.segunfrancis.firebase.di

import com.project.segunfrancis.domain.repository.FirebaseRepository
import com.project.segunfrancis.firebase.FirebaseRepositoryImpl
import com.project.segunfrancis.firebase.data.Database
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val firebaseModule = module {
    single<FirebaseRepository> { FirebaseRepositoryImpl(Database.init(), Dispatchers.IO, Database.getUserId()) }
}
