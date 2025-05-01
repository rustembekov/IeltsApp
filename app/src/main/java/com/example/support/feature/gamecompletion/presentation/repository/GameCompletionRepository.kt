package com.example.support.feature.gamecompletion.presentation.repository

import android.util.Log
import com.example.support.core.util.ResultCore
import com.example.support.core.domain.User
import com.example.support.feature.gamecompletion.presentation.domain.GameResultDelta
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GameCompletionRepository {
    suspend fun processGameResult(userId: String, newScore: Int): ResultCore<GameResultDelta>
}

class GameCompletionRepositoryImpl @Inject constructor(
    private val database: DatabaseReference
) : GameCompletionRepository {

    override suspend fun processGameResult(userId: String, newScore: Int): ResultCore<GameResultDelta> =
        withContext(Dispatchers.IO) {
            try {
                // 1. Get current user
                val snapshot = database.child("users").child(userId).get().await()
                val user = snapshot.getValue(User::class.java)?.copy(id = userId)
                    ?: return@withContext ResultCore.Failure("User not found")

                val previousScore = user.score
                val previousRank = user.rank

                // 2. Calculate new score
                val updatedScore = previousScore + newScore

                // 3. Update user score
                database.child("users").child(userId).child("score").setValue(updatedScore).await()

                // 4. Recalculate all ranks
                val allUsersSnapshot = database.child("users").get().await()
                val users = allUsersSnapshot.children.mapNotNull { child ->
                    val u = child.getValue(User::class.java)
                    u?.copy(id = child.key ?: "")
                }.filter { it.score != null }
                    .sortedByDescending { it.score }

                val updates = mutableMapOf<String, Any>()
                users.forEachIndexed { index, u ->
                    updates["users/${u.id}/rank"] = index + 1
                }

                database.updateChildren(updates).await()

                // 5. Find new rank
                val newRank = users.indexOfFirst { it.id == userId } + 1

                ResultCore.Success(
                    GameResultDelta(
                        previousScore = previousScore,
                        previousRank = previousRank,
                        newScore = updatedScore,
                        newRank = newRank
                    )
                )

            } catch (e: Exception) {
                Log.e("GameCompletionRepo", "Error: ${e.message}", e)
                ResultCore.Failure(e.message ?: "Unknown error")
            }
        }
}
