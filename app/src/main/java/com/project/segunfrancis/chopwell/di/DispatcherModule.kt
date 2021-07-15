package com.project.segunfrancis.chopwell.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dispatcherModule = module {
    single { Dispatchers.IO }
}
