package com.project.segunfrancis.chopwell.presentation.ui.favorites

import com.project.segunfrancis.usecase.meal_usecase.GetAllFavoritesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {

    single { GetAllFavoritesUseCase(get(), get()) }

    viewModel { FavoriteViewModel(get()) }
}
