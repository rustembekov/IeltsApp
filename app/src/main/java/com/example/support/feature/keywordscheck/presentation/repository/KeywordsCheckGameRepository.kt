package com.example.support.feature.keywordscheck.presentation.repository

import com.example.support.feature.keywordscheck.presentation.data.KeywordsCheckGame
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import com.example.support.core.util.ResultCore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class KeywordsCheckGameRepository @Inject constructor(
    private val database: DatabaseReference
) {

    suspend fun getAllQuestionIds(): ResultCore<List<String>> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            database.child("games").child("keywords_check").get()
                .addOnSuccessListener { snapshot ->
                    val ids = snapshot.children.mapNotNull { it.key }
                    continuation.resume(ResultCore.Success(ids))
                }
                .addOnFailureListener { e ->
                    continuation.resume(ResultCore.Failure(e.message ?: "Failed to load IDs"))
                }
        }
    }

    suspend fun getQuestionById(id: String): ResultCore<KeywordsCheckGame> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            database.child("games").child("keywords_check").child(id).get()
                .addOnSuccessListener { snapshot ->
                    val question = snapshot.getValue(KeywordsCheckGame::class.java)
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
            KeywordsCheckGame(
                text = "Climate change has led to extreme weather, causing hurricanes, droughts, and floods, prompting efforts to cut emissions and promote sustainability.",
                answers = listOf("climate change", "extreme weather", "emissions", "sustainability")
            ),
            KeywordsCheckGame(
                text = "The rapid growth of urban populations has increased pressure on housing, transportation, and infrastructure systems across major cities.",
                answers = listOf("urban populations", "housing", "transportation", "infrastructure")
            ),
            KeywordsCheckGame(
                text = "The invention of the steam engine during the Industrial Revolution revolutionized manufacturing processes, allowing goods to be produced on a mass scale.",
                answers = listOf("steam engine", "Industrial Revolution", "manufacturing", "mass scale")
            ),
            KeywordsCheckGame(
                text = "Advances in genetic engineering have opened new possibilities in medicine, agriculture, and biotechnology, raising both excitement and ethical concerns.",
                answers = listOf("genetic engineering", "medicine", "agriculture", "biotechnology")
            ),
            KeywordsCheckGame(
                text = "Renewable energy technologies such as solar panels, wind turbines, and hydroelectric dams are essential for reducing our dependence on fossil fuels.",
                answers = listOf("renewable energy", "solar panels", "wind turbines", "fossil fuels")
            ),
            KeywordsCheckGame(
                text = "Scientists continue to explore deep ocean ecosystems, discovering new species and uncovering mysteries about marine biodiversity and ecosystem health.",
                answers = listOf("deep ocean", "marine biodiversity", "ecosystem health")
            ),
            KeywordsCheckGame(
                text = "The destruction of the Amazon rainforest due to deforestation, mining, and agriculture threatens countless species and accelerates global warming.",
                answers = listOf("Amazon rainforest", "deforestation", "global warming")
            ),
            KeywordsCheckGame(
                text = "The expansion of global trade has connected markets, improved economic growth, but also heightened the risk of supply chain disruptions.",
                answers = listOf("global trade", "economic growth", "supply chain disruptions")
            ),
            KeywordsCheckGame(
                text = "Artificial intelligence has begun transforming industries such as healthcare, education, and finance, raising questions about automation and employment.",
                answers = listOf("artificial intelligence", "automation", "employment")
            ),
            KeywordsCheckGame(
                text = "Improvements in public transportation systems, such as electric buses and subway networks, can significantly reduce urban pollution and traffic congestion.",
                answers = listOf("public transportation", "electric buses", "urban pollution", "traffic congestion")
            ),
            KeywordsCheckGame(
                text = "Space exploration has led to incredible technological advancements, inspiring the development of satellite communication, GPS systems, and materials science.",
                answers = listOf("space exploration", "satellite communication", "GPS systems", "materials science")
            ),
            KeywordsCheckGame(
                text = "Rising ocean temperatures have contributed to widespread coral bleaching, threatening the survival of entire marine ecosystems and coastal economies.",
                answers = listOf("ocean temperatures", "coral bleaching", "marine ecosystems", "coastal economies")
            ),
            KeywordsCheckGame(
                text = "Historical landmarks like the Great Wall of China and Machu Picchu offer insights into ancient engineering techniques and cultural traditions.",
                answers = listOf("Great Wall of China", "Machu Picchu", "engineering techniques", "cultural traditions")
            ),
            KeywordsCheckGame(
                text = "Microplastic pollution has emerged as a major threat to marine life, as tiny plastic particles are now found in the bodies of fish, seabirds, and even humans.",
                answers = listOf("microplastic pollution", "marine life", "plastic particles")
            ),
            KeywordsCheckGame(
                text = "Renewable agriculture practices such as crop rotation, organic farming, and permaculture aim to protect soil health and ensure long-term food security.",
                answers = listOf("renewable agriculture", "crop rotation", "organic farming", "soil health", "food security")
            ),
            KeywordsCheckGame(
                text = "The development of 5G technology promises faster internet speeds, enhanced mobile connectivity, and new possibilities for smart cities and autonomous vehicles.",
                answers = listOf("5G technology", "internet speeds", "smart cities", "autonomous vehicles")
            ),
            KeywordsCheckGame(
                text = "Scientists working in Antarctica are studying the effects of climate change on ice sheets, providing crucial data on rising sea levels.",
                answers = listOf("Antarctica", "climate change", "ice sheets", "sea levels")
            ),
            KeywordsCheckGame(
                text = "Mental health awareness has increased globally, encouraging more discussions about anxiety, depression, and the importance of psychological well-being.",
                answers = listOf("mental health", "anxiety", "depression", "psychological well-being")
            ),
            KeywordsCheckGame(
                text = "The rise of e-commerce platforms like Amazon and Alibaba has fundamentally changed shopping habits, impacting traditional retail stores worldwide.",
                answers = listOf("e-commerce", "Amazon", "Alibaba", "retail stores")
            ),
            KeywordsCheckGame(
                text = "The discovery of water on Mars has reignited interest in space colonization, with researchers focusing on how humans might survive on other planets.",
                answers = listOf("water on Mars", "space colonization", "other planets")
            )
        )

        questions.forEach { question ->
            val id = database.child("games").child("keywords_check").push().key ?: return@forEach
            database.child("games").child("keywords_check").child(id).setValue(question.copy(id = id))
        }
    }
}
