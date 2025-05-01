package com.example.support.feature.phrasalverbs.presentation.repository

import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import com.example.support.core.util.ResultCore
import com.example.support.feature.phrasalverbs.presentation.data.PhrasalVerbsGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PhrasalVerbsGameRepository @Inject constructor(
    private val database: DatabaseReference
) {

    suspend fun getAllQuestionIds(): ResultCore<List<String>> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            database.child("games").child("phrasal_verbs").get()
                .addOnSuccessListener { snapshot ->
                    val ids = snapshot.children.mapNotNull { it.key }
                    continuation.resume(ResultCore.Success(ids))
                }
                .addOnFailureListener { e ->
                    continuation.resume(ResultCore.Failure(e.message ?: "Failed to load IDs"))
                }
        }
    }

    suspend fun getQuestionById(id: String): ResultCore<PhrasalVerbsGame> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            database.child("games").child("phrasal_verbs").child(id).get()
                .addOnSuccessListener { snapshot ->
                    val question = snapshot.getValue(PhrasalVerbsGame::class.java)
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

    fun insertInitialData() {
        val questions = listOf(
            PhrasalVerbsGame(text = "After a long and stressful day at work, I just want to ________ on the couch and watch my favorite show.", answer = "chill out"),
            PhrasalVerbsGame(text = "Because of the bad weather forecast, we need to ________ the outdoor event until next weekend.", answer = "put off"),
            PhrasalVerbsGame(text = "Can you please remember to ________ all the lights when you leave the office at night?", answer = "turn off"),
            PhrasalVerbsGame(text = "Last year, she decided to ________ painting as a way to relax and express herself.", answer = "take up"),
            PhrasalVerbsGame(text = "I was so confused during the call that I couldn't ________ what he was trying to explain.", answer = "make out"),
            PhrasalVerbsGame(text = "Their car suddenly ________ in the middle of the highway, causing a huge traffic jam.", answer = "broke down"),
            PhrasalVerbsGame(text = "Before we finalize the proposal, let’s ________ the main points one more time to be sure.", answer = "go over"),
            PhrasalVerbsGame(text = "Everyone says that Anna ________ her mother, especially when she smiles.", answer = "takes after"),
            PhrasalVerbsGame(text = "Please remember to ________ your muddy shoes before stepping into the living room.", answer = "take off"),
            PhrasalVerbsGame(text = "After many arguments and disagreements, they finally ________ and went their separate ways.", answer = "split up"),
            PhrasalVerbsGame(text = "We should ________ all the project details carefully before we agree to start working.", answer = "work out"),
            PhrasalVerbsGame(text = "No matter how hard it gets, you should never ________ on achieving your dreams.", answer = "give up"),
            PhrasalVerbsGame(text = "Could you please ________ this application form and submit it before 5 PM today?", answer = "fill out"),
            PhrasalVerbsGame(text = "After weeks in the hospital, he finally ________ and returned home much healthier.", answer = "pulled through"),
            PhrasalVerbsGame(text = "I’m trying to ________ enough money to go on a trip to Europe next summer.", answer = "save up"),
            PhrasalVerbsGame(text = "She ________ a story about being late because she missed the bus, but it wasn’t true.", answer = "made up"),
            PhrasalVerbsGame(text = "I really want to ________ some of my old friends from high school to catch up.", answer = "get in touch with"),
            PhrasalVerbsGame(text = "During the brainstorming session, he ________ a fantastic marketing idea that everyone loved.", answer = "came up with"),
            PhrasalVerbsGame(text = "I always ________ difficult vocabulary words in my notebook so I can review them later.", answer = "write down"),
            PhrasalVerbsGame(text = "Let’s ________ early tomorrow morning so we can avoid the heavy traffic and arrive on time.", answer = "set off")
        )

        questions.forEach { question ->
            val id = database.child("games").child("phrasal_verbs").push().key ?: return@forEach
            database.child("games").child("phrasal_verbs").child(id).setValue(question.copy(id = id))
        }
    }
}
