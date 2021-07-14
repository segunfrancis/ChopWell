package com.project.segunfrancis.domain.repository

import com.project.segunfrancis.domain.util.toDomain
import com.project.segunfrancis.domain.util.toUseCase
import com.project.segunfrancis.usecase.model.MealUC
import com.project.segunfrancis.usecase.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MealRepositoryImpl(private val firebaseRepository: FirebaseRepository) : MealRepository {
    override fun addFavorite(meal: MealUC): Flow<Boolean> {
        return flow { emit(firebaseRepository.addFavoriteAsync(meal.toDomain()).await()) }
    }

    override fun removeFavorite(meal: MealUC): Flow<Boolean> {
        return flow { emit(firebaseRepository.removeFavoriteAsync(meal.toDomain()).await()) }
    }

    override fun getAllFavorites(): Flow<List<MealUC?>?> {
        return flow { emit(firebaseRepository.getAllFavoritesAsync().await()?.map { it?.toUseCase() }) }
    }

    override fun clearAllFavorites() {
        TODO("Not yet implemented")
    }

    override fun addMeal(meal: MealUC) {
        TODO("Not yet implemented")
    }

    override fun getAllMeals(category: String) {
        TODO("Not yet implemented")
    }

    override fun signIn() {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun enablePersistence() {
        TODO("Not yet implemented")
    }
}