package com.example.support.feature.factopinion.presentation.repository

import com.example.support.feature.factopinion.presentation.data.FactOpinionGame
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.example.support.core.util.ResultCore

class FactOpinionGameRepository @Inject constructor(
    private val database: DatabaseReference
) {

    suspend fun getAllQuestionIds(): ResultCore<List<String>> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            database.child("games").child("fact_opinion").get()
                .addOnSuccessListener { snapshot ->
                    val ids = snapshot.children.mapNotNull { it.key }
                    continuation.resume(ResultCore.Success(ids))
                }
                .addOnFailureListener { e ->
                    continuation.resume(ResultCore.Failure(e.message ?: "Failed to load IDs"))
                }
        }
    }

    suspend fun getQuestionById(id: String): ResultCore<FactOpinionGame> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            database.child("games").child("fact_opinion").child(id).get()
                .addOnSuccessListener { snapshot ->
                    val question = snapshot.getValue(FactOpinionGame::class.java)
                    if (question != null) {
                        continuation.resume(ResultCore.Success(question))
                    } else {
                        continuation.resume(ResultCore.Failure("Question not found"))
                    }
                }
                .addOnFailureListener { e ->
                    continuation.resume(ResultCore.Failure(e.message ?: "Failed to get question"))
                }
        }
    }
}




/* TODO First run:
fun insertInitialData() {
        val questions = listOf(
            FactOpinionGame(text = "Artificial intelligence will eventually replace most human jobs.", answer = "Opinion"),
            FactOpinionGame(text = "The human brain contains approximately 86 billion neurons.", answer = "Fact"),
            FactOpinionGame(text = "Online education is more effective than traditional classroom learning.", answer = "Opinion"),
            FactOpinionGame(text = "The Treaty of Versailles was signed in 1919 and officially ended World War I.", answer = "Fact"),
            FactOpinionGame(text = "Climate change is the most critical issue facing humanity today.", answer = "Opinion"),
            FactOpinionGame(text = "Shakespeare’s works have been translated into over 100 languages.", answer = "Fact"),
            FactOpinionGame(text = "A democratic government is the best form of governance for all societies.", answer = "Opinion"),
            FactOpinionGame(text = "Mount Everest is the highest mountain above sea level, standing at 8,848.86 meters.", answer = "Fact"),
            FactOpinionGame(text = "Reading books is the best way to gain knowledge.", answer = "Opinion")
        )

        questions.forEach { question ->
            database.child("games").child("fact_opinion").child(question.id).setValue(question)
        }
    }



    fun renameNodeAndInsertData() {
        val oldRef = database.child("games").child("secondGame")
        val newRef = database.child("games").child("fact_opinion")

        oldRef.get().addOnSuccessListener { snapshot ->
            // Copy all data to new node
            newRef.setValue(snapshot.value).addOnSuccessListener {
                // Delete the old node after copying
                oldRef.removeValue().addOnSuccessListener {
                    Log.d("Firebase", "Renamed node and deleted old one successfully.")
                }.addOnFailureListener {
                    Log.e("Firebase", "Failed to delete old node: ${it.message}")
                }
            }.addOnFailureListener {
                Log.e("Firebase", "Failed to set new node: ${it.message}")
            }
        }.addOnFailureListener {
            Log.e("Firebase", "Failed to read old node: ${it.message}")
        }
    }
 */