package com.project.segunfrancis.chopwell.presentation.ui.meal_list

import com.project.segunfrancis.usecase.meal_usecase.GetAllMealsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mealListModule = module {

    single { GetAllMealsUseCase(get(), get()) }

    viewModel { MealListViewModel(get()) }
}
