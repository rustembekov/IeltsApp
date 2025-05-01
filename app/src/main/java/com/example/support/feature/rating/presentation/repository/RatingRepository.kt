package com.example.support.feature.rating.presentation.repository

import android.util.Log
import com.example.support.core.domain.User
import com.example.support.core.util.ResultCore
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatingRepository @Inject constructor(
    private val database: DatabaseReference
) {
    suspend fun getUsersByRating(): ResultCore<List<User>> = withContext(Dispatchers.IO) {
        try {
            val snapshot = database.child("users").orderByChild("score").get().await()
            val users = snapshot.children.mapNotNull { child ->
                val user = child.getValue(User::class.java)
                user?.copy(id = child.key ?: "")
            }.filter { it.score >= 0 }
                .sortedByDescending { it.score }

            ResultCore.Success(users)
        } catch (e: Exception) {
            Log.e("RatingRepository", "Error fetching users: ${e.message}", e)
            ResultCore.Failure(e.message ?: "Unknown error")
        }
    }


    suspend fun updateUserRanks() {
        withContext(Dispatchers.IO) {
            try {
                val snapshot = database.child("users").get().await()
                val users = snapshot.children.mapNotNull { child ->
                    val user = child.getValue(User::class.java)
                    user?.copy(id = child.key ?: "")
                }.filter { it.score != null } // Убираем `null`
                    .sortedByDescending { it.score }

                if (users.isEmpty()) return@withContext // Защита от пустого списка

                val updates = mutableMapOf<String, Any>()
                users.forEachIndexed { index, user ->
                    val rank = index + 1
                    updates["users/${user.id}/rank"] = rank
                }

                database.updateChildren(updates).await()
                Log.d("RatingRepository", "Ranks updated successfully")
            } catch (e: Exception) {
                Log.e("RatingRepository", "Failed to update ranks: ${e.message}")
            }
        }
    }

    suspend fun getUser(userId: String): User? = withContext(Dispatchers.IO) {
        return@withContext try {
            val snapshot = database.child("users").child(userId).get().await()
            snapshot.getValue(User::class.java)?.copy(id = userId)
        } catch (e: Exception) {
            null
        }
    }
}
