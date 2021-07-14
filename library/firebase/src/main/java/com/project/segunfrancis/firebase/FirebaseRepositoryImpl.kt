package com.project.segunfrancis.firebase

import com.google.firebase.database.FirebaseDatabase
import com.project.segunfrancis.domain.model.MealDomain
import com.project.segunfrancis.domain.repository.FirebaseRepository
import com.project.segunfrancis.firebase.model.MealEntity
import com.project.segunfrancis.firebase.utils.FirebaseConstants.FAVORITES
import com.project.segunfrancis.firebase.utils.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class FirebaseRepositoryImpl(
    private val database: FirebaseDatabase,
    private val dispatcher: CoroutineDispatcher,
    private val userId: String
) : FirebaseRepository {
    override suspend fun addFavoriteAsync(meal: MealDomain): Deferred<Boolean> = coroutineScope {
        async(dispatcher) {
            database.reference.child(FAVORITES).child(meal.userId).child(meal.id)
                .setValue(meal).isSuccessful
        }
    }

    override suspend fun removeFavoriteAsync(meal: MealDomain): Deferred<Boolean> = coroutineScope {
        async(dispatcher) {
            database.reference.child(FAVORITES).child(meal.userId).child(meal.id)
                .removeValue().isSuccessful
        }
    }

    override suspend fun getAllFavoritesAsync() : Deferred<List<MealDomain?>?> = coroutineScope {
        async(dispatcher) {
            database.reference.child(FAVORITES).child(userId)
                .get().result?.children?.map { snapshot ->
                snapshot.getValue(MealEntity::class.java)?.toDomain()
            }
        }
    }

    override fun clearAllFavorites() {
        TODO("Not yet implemented")
    }

    override fun addMeal(meal: MealDomain) {
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
