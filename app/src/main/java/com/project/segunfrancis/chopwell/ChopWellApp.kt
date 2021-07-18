package com.project.segunfrancis.chopwell

import android.app.Application
import com.project.segunfrancis.chopwell.di.dispatcherModule
import com.project.segunfrancis.chopwell.di.repositoryModule
import com.project.segunfrancis.chopwell.presentation.ui.favorites.favoriteModule
import com.project.segunfrancis.chopwell.presentation.ui.meal_list.mealListModule
import com.project.segunfrancis.chopwell.presentation.ui.search.searchModule
import com.project.segunfrancis.firebase.di.authModule
import com.project.segunfrancis.firebase.di.firebaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class ChopWellApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ChopWellApp)
            modules(
                dispatcherModule,
                firebaseModule,
                authModule,
                repositoryModule,
                favoriteModule,
                mealListModule,
                searchModule
            )
        }
    }
}
