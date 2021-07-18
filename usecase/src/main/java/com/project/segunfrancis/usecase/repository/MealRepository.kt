package com.project.segunfrancis.usecase.repository

import com.project.segunfrancis.usecase.model.MealUC
import kotlinx.coroutines.flow.Flow

/**
 * Should contain all the methods in the project
 */

interface MealRepository {
    fun addFavorite(meal: MealUC): Flow<Boolean>

    fun removeFavorite(meal: MealUC): Flow<Boolean>

    fun getAllFavorites(): Flow<List<MealUC?>?>

    fun clearAllFavorites()

    fun addMeal(meal: MealUC)

    fun getAllMeals(category: String): Flow<List<MealUC?>?>

    fun searchMeals(query: String): Flow<List<MealUC?>?>
}
