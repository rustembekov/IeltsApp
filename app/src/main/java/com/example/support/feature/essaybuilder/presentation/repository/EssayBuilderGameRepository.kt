package com.example.support.feature.essaybuilder.presentation.repository

import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import com.example.support.core.util.ResultCore
import com.example.support.feature.essaybuilder.presentation.data.EssayBuilderGame
import com.example.support.feature.essaybuilder.presentation.data.QuestionPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class EssayBuilderGameRepository @Inject constructor(
    private val database: DatabaseReference
) {

    suspend fun getAllQuestionIds(): ResultCore<List<String>> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            database.child("games").child("essay_builder").get()
                .addOnSuccessListener { snapshot ->
                    val ids = snapshot.children.mapNotNull { it.key }
                    continuation.resume(ResultCore.Success(ids))
                }
                .addOnFailureListener { e ->
                    continuation.resume(ResultCore.Failure(e.message ?: "Failed to load IDs"))
                }
        }
    }

    suspend fun getQuestionById(id: String): ResultCore<EssayBuilderGame> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            database.child("games").child("essay_builder").child(id).get()
                .addOnSuccessListener { snapshot ->
                    val game = snapshot.getValue(EssayBuilderGame::class.java)
                    if (game != null) {
                        continuation.resume(ResultCore.Success(game))
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
        val games = listOf(
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "In modern society, "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " has become a major concern for governments worldwide. Addressing this issue is essential to ensure a "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " future for the next generations.")
                ),
                options = listOf("pollution", "education", "sustainable", "dangerous"),
                correctAnswers = listOf("pollution", "sustainable")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "The rise of "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " has significantly changed the way people communicate. This technological shift has led to both positive and "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " consequences for society.")
                ),
                options = listOf("social media", "transportation", "harmful", "beneficial"),
                correctAnswers = listOf("social media", "beneficial")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "Many people believe that "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " should be made free to promote equal opportunities. However, some argue that the "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " costs involved would burden taxpayers.")
                ),
                options = listOf("healthcare", "education", "financial", "emotional"),
                correctAnswers = listOf("education", "financial")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "While some argue that "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " should be banned completely, others believe it has economic benefits. Nevertheless, its negative impact on the "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " cannot be ignored.")
                ),
                options = listOf("smoking", "agriculture", "environment", "technology"),
                correctAnswers = listOf("smoking", "environment")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "The popularity of "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " diets has grown rapidly in recent years. Supporters claim that such lifestyles are more "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " for the planet.")
                ),
                options = listOf("vegan", "balanced", "healthy", "sustainable"),
                correctAnswers = listOf("vegan", "sustainable")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "A strong "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " system is essential for the success of any modern economy. Without it, businesses may struggle to transport goods "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " and reliably.")
                ),
                options = listOf("education", "transportation", "efficiently", "safely"),
                correctAnswers = listOf("transportation", "efficiently")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "Many experts argue that "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " education should be introduced earlier in schools. Teaching students financial responsibility at a young age can prevent future "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", ".")
                ),
                options = listOf("financial", "environmental", "debt", "pollution"),
                correctAnswers = listOf("financial", "debt")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "Rising levels of "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " have become a pressing issue in large urban areas. Governments must take immediate action to improve air quality and protect "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " health.")
                ),
                options = listOf("pollution", "globalization", "mental", "public"),
                correctAnswers = listOf("pollution", "public")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "The widespread use of "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " devices has altered the way students learn today. However, excessive screen time can lead to "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " health problems.")
                ),
                options = listOf("mobile", "fitness", "physical", "emotional"),
                correctAnswers = listOf("mobile", "physical")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "Advancements in "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " have enabled doctors to diagnose diseases more accurately. This has contributed to an increase in "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " expectancy across the globe.")
                ),
                options = listOf("technology", "economy", "life", "employment"),
                correctAnswers = listOf("technology", "life")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "The government must implement stricter "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " regulations to protect natural resources. Without immediate action, the "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " of endangered species may become inevitable.")
                ),
                options = listOf("environmental", "financial", "extinction", "migration"),
                correctAnswers = listOf("environmental", "extinction")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "The concept of "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " learning has gained popularity in recent years. It allows students to access educational materials "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " through the Internet.")
                ),
                options = listOf("online", "distant", "remotely", "physically"),
                correctAnswers = listOf("online", "remotely")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "Many cities are investing in "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " transportation to reduce carbon emissions. This move is seen as a step toward creating more "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " urban environments.")
                ),
                options = listOf("public", "global", "sustainable", "dangerous"),
                correctAnswers = listOf("public", "sustainable")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "Despite the benefits of "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " working, some employees report feeling isolated. Building strong team communication is key to maintaining a "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " workplace culture.")
                ),
                options = listOf("remote", "physical", "positive", "negative"),
                correctAnswers = listOf("remote", "positive")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "The introduction of "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " intelligence into healthcare has improved patient care. However, concerns about data privacy and "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " ethics remain unresolved.")
                ),
                options = listOf("artificial", "agricultural", "medical", "technological"),
                correctAnswers = listOf("artificial", "technological")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "Investment in "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " development is essential for reducing reliance on fossil fuels. Without sustainable alternatives, the planet’s "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " resources will be depleted.")
                ),
                options = listOf("renewable", "industrial", "natural", "artificial"),
                correctAnswers = listOf("renewable", "natural")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "Learning a "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " language can open many professional opportunities. Moreover, it often enhances cultural "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " and global understanding.")
                ),
                options = listOf("second", "major", "awareness", "distance"),
                correctAnswers = listOf("second", "awareness")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "Governments should prioritize "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " reform to improve access to justice. An efficient legal system is crucial for maintaining "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " and public trust.")
                ),
                options = listOf("legal", "financial", "order", "unemployment"),
                correctAnswers = listOf("legal", "order")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "Rising "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " costs are placing a burden on families around the world. Implementing policies to make housing more "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " would benefit society greatly.")
                ),
                options = listOf("housing", "health", "affordable", "expensive"),
                correctAnswers = listOf("housing", "affordable")
            ),
            EssayBuilderGame(
                questionParts = listOf(
                    QuestionPart("text", "Programs promoting "),
                    QuestionPart("blank", index = 0),
                    QuestionPart("text", " tourism aim to minimize the negative impacts of travel. They encourage visitors to respect local cultures and protect "),
                    QuestionPart("blank", index = 1),
                    QuestionPart("text", " landmarks.")
                ),
                options = listOf("sustainable", "educational", "historical", "artistic"),
                correctAnswers = listOf("sustainable", "historical")
            )
        )

        games.forEach { game ->
            val id = database.child("games").child("essay_builder").push().key ?: return@forEach
            database.child("games").child("essay_builder").child(id).setValue(game.copy(id = id))
        }
    }
}
