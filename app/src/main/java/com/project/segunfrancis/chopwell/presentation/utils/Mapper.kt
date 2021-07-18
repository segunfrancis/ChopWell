package com.project.segunfrancis.chopwell.presentation.utils

import com.project.segunfrancis.chopwell.entity.MealEntity
import com.project.segunfrancis.usecase.model.MealUC

fun MealUC.toMealEntity(): MealEntity {
    return MealEntity(
        id,
        category,
        mealName,
        imageURL,
        description,
        preparation,
        recipe,
        queryMealName
    )
}