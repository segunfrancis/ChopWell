package com.project.segunfrancis.chopwell.presentation.ui.search

import com.project.segunfrancis.usecase.meal_usecase.SearchMealUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    single { SearchMealUseCase(get(), get()) }

    viewModel { SearchViewModel(get()) }
}
