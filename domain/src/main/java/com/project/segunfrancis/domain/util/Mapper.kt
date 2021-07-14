package com.project.segunfrancis.domain.util

import com.project.segunfrancis.domain.model.MealDomain
import com.project.segunfrancis.usecase.model.MealUC

fun MealDomain.toUseCase(): MealUC {
    return MealUC(
        id,
        category,
        mealName,
        imageURL,
        description,
        preparation,
        recipe,
        userId,
        userEmail,
        queryMealName
    )
}

fun MealUC.toDomain(): MealDomain {
    return MealDomain(
        id,
        category,
        mealName,
        imageURL,
        description,
        preparation,
        recipe,
        userId,
        userEmail,
        queryMealName
    )
}
