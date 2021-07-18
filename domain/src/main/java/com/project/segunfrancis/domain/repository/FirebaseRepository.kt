package com.project.segunfrancis.domain.repository

import com.project.segunfrancis.domain.model.MealDomain
import kotlinx.coroutines.Deferred

interface FirebaseRepository {
    suspend fun addFavoriteAsync(meal: MealDomain): Deferred<Boolean>

    suspend fun removeFavoriteAsync(meal: MealDomain): Deferred<Boolean>

    suspend fun getAllFavoritesAsync() : Deferred<List<MealDomain?>?>

    fun clearAllFavorites()

    fun addMeal(meal: MealDomain)

    suspend fun getAllMealsAsync(category: String) : Deferred<List<MealDomain?>?>
}
