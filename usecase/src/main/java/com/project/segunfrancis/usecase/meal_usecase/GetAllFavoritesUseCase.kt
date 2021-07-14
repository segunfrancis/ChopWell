package com.project.segunfrancis.usecase.meal_usecase

import com.project.segunfrancis.usecase.FlowUseCase
import com.project.segunfrancis.usecase.model.MealUC
import com.project.segunfrancis.usecase.repository.MealRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetAllFavoritesUseCase(
    override val dispatcher: CoroutineDispatcher,
    private val repository: MealRepository
) : FlowUseCase<Unit, List<MealUC?>?>() {
    override fun buildFlowUseCase(data: Unit?): Flow<List<MealUC?>?> {
        return repository.getAllFavorites()
    }
}
