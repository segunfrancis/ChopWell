package com.project.segunfrancis.firebase.utils

import com.project.segunfrancis.domain.model.MealDomain
import com.project.segunfrancis.firebase.model.MealEntity

fun MealEntity.toDomain(): MealDomain {
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