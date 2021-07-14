package com.project.segunfrancis.usecase.meal_usecase

import com.project.segunfrancis.usecase.FlowUseCase
import com.project.segunfrancis.usecase.model.MealUC
import com.project.segunfrancis.usecase.repository.MealRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class RemoveFavoriteUseCase(
    override val dispatcher: CoroutineDispatcher,
    private val repository: MealRepository
) : FlowUseCase<MealUC, Boolean>() {
    override fun buildFlowUseCase(data: MealUC?): Flow<Boolean> {
        return repository.removeFavorite(data!!)
    }
}
