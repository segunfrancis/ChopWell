package com.project.segunfrancis.chopwell

import android.app.Application
import com.project.segunfrancis.chopwell.presentation.di.firebaseModule
import com.project.segunfrancis.chopwell.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChopWellApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ChopWellApp)
            modules(viewModelModule, firebaseModule)
        }
    }
}