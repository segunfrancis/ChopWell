package com.project.segunfrancis.chopwell.di

import com.project.segunfrancis.domain.repository.MealRepositoryImpl
import com.project.segunfrancis.usecase.repository.MealRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MealRepository> { MealRepositoryImpl(get()) }
}

