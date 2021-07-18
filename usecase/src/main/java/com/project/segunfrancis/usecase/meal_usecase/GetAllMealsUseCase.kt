package com.project.segunfrancis.usecase.meal_usecase

import com.project.segunfrancis.usecase.FlowUseCase
import com.project.segunfrancis.usecase.model.MealUC
import com.project.segunfrancis.usecase.repository.MealRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetAllMealsUseCase(
    override val dispatcher: CoroutineDispatcher,
    private val repository: MealRepository
): FlowUseCase<String, List<MealUC?>?>() {
    override fun buildFlowUseCase(data: String?): Flow<List<MealUC?>?> {
        return repository.getAllMeals(data!!)
    }
}
