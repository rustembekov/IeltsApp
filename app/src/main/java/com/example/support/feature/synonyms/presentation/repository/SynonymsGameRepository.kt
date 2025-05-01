package com.example.support.feature.synonyms.presentation.repository

import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import com.example.support.core.util.ResultCore
import com.example.support.feature.synonyms.presentation.data.SynonymsGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SynonymsGameRepository @Inject constructor(
    private val database: DatabaseReference
) {

    suspend fun getAllQuestionIds(): ResultCore<List<String>> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            database.child("games").child("synonyms").get()
                .addOnSuccessListener { snapshot ->
                    val ids = snapshot.children.mapNotNull { it.key }
                    continuation.resume(ResultCore.Success(ids))
                }
                .addOnFailureListener { e ->
                    continuation.resume(ResultCore.Failure(e.message ?: "Failed to load IDs"))
                }
        }
    }

    suspend fun getQuestionById(id: String): ResultCore<SynonymsGame> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            database.child("games").child("synonyms").child(id).get()
                .addOnSuccessListener { snapshot ->
                    val question = snapshot.getValue(SynonymsGame::class.java)
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
            SynonymsGame(
                id = "1",
                category = "Environment",
                word = "Pollutants",
                synonyms = listOf("contaminants", "toxins", "poisons"),
                otherWords = listOf("aggressors", "erosion", "deforestation")
            ),
            SynonymsGame(
                id = "2",
                category = "Environment",
                word = "Endangered",
                synonyms = listOf("at risk", "vulnerable", "threatened"),
                otherWords = listOf("rebellious", "outdated", "fortunate")
            ),
            SynonymsGame(
                id = "3",
                category = "Environment",
                word = "Deforestation",
                synonyms = listOf("logging", "clear-cutting", "forest loss"),
                otherWords = listOf("architecture", "irrigation", "atmosphere")
            ),
            SynonymsGame(
                id = "4",
                category = "Environment",
                word = "Renewable",
                synonyms = listOf("sustainable", "inexhaustible", "replenishable"),
                otherWords = listOf("exhausted", "finite", "irrelevant")
            ),
            SynonymsGame(
                id = "5",
                category = "Environment",
                word = "Emissions",
                synonyms = listOf("discharge", "output", "pollution"),
                otherWords = listOf("inclusion", "reception", "erosion")
            ),
            SynonymsGame(
                id = "6",
                category = "Environment",
                word = "Sustainability",
                synonyms = listOf("viability", "durability", "resilience"),
                otherWords = listOf("imbalance", "instability", "uncertainty")
            ),
            SynonymsGame(
                id = "7",
                category = "Environment",
                word = "Biodiversity",
                synonyms = listOf("ecosystem variety", "biological richness", "species diversity"),
                otherWords = listOf("uniformity", "singularity", "mechanism")
            ),
            SynonymsGame(
                id = "8",
                category = "Environment",
                word = "Climate change",
                synonyms = listOf("global warming", "climate shift", "environmental change"),
                otherWords = listOf("stagnation", "repetition", "architecture")
            ),
            SynonymsGame(
                id = "9",
                category = "Environment",
                word = "Conservation",
                synonyms = listOf("preservation", "protection", "maintenance"),
                otherWords = listOf("destruction", "ignorance", "consumption")
            ),
            SynonymsGame(
                id = "10",
                category = "Environment",
                word = "Degradation",
                synonyms = listOf("deterioration", "decline", "corrosion"),
                otherWords = listOf("improvement", "construction", "enhancement")
            ),
            SynonymsGame(
                id = "11",
                category = "Society",
                word = "Inequality",
                synonyms = listOf("disparity", "imbalance", "injustice"),
                otherWords = listOf("routine", "innovation", "location")
            ),
            SynonymsGame(
                id = "12",
                category = "Society",
                word = "Welfare",
                synonyms = listOf("well-being", "aid", "social security"),
                otherWords = listOf("dictatorship", "oppression", "negligence")
            ),
            SynonymsGame(
                id = "13",
                category = "Society",
                word = "Discrimination",
                synonyms = listOf("prejudice", "bias", "exclusion"),
                otherWords = listOf("combination", "inclusion", "expansion")
            ),
            SynonymsGame(
                id = "14",
                category = "Society",
                word = "Integration",
                synonyms = listOf("inclusion", "incorporation", "assimilation"),
                otherWords = listOf("separation", "division", "extinction")
            ),
            SynonymsGame(
                id = "15",
                category = "Society",
                word = "Urbanization",
                synonyms = listOf("city expansion", "modernization", "metropolitan growth"),
                otherWords = listOf("agriculture", "industry", "mountains")
            ),
            SynonymsGame(
                id = "16",
                category = "Society",
                word = "Cultural diversity",
                synonyms = listOf("multiculturalism", "ethnic variety", "pluralism"),
                otherWords = listOf("uniformity", "monotony", "specialization")
            ),
            SynonymsGame(
                id = "17",
                category = "Society",
                word = "Social mobility",
                synonyms = listOf("upward movement", "career progression", "economic advancement"),
                otherWords = listOf("imprisonment", "isolation", "detainment")
            ),
            SynonymsGame(
                id = "18",
                category = "Society",
                word = "Poverty",
                synonyms = listOf("destitution", "deprivation", "hardship"),
                otherWords = listOf("luxury", "inheritance", "monopoly")
            ),
            SynonymsGame(
                id = "19",
                category = "Society",
                word = "Marginalized",
                synonyms = listOf("excluded", "disregarded", "oppressed"),
                otherWords = listOf("famous", "central", "authoritative")
            ),
            SynonymsGame(
                id = "20",
                category = "Society",
                word = "Demographics",
                synonyms = listOf("population statistics", "census data", "social composition"),
                otherWords = listOf("philosophy", "hypothesis", "dimension")
            ),
            SynonymsGame(
                id = "21",
                category = "Education",
                word = "Curriculum",
                synonyms = listOf("syllabus", "coursework", "study plan"),
                otherWords = listOf("legislation", "medicine", "preference")
            ),
            SynonymsGame(
                id = "22",
                category = "Education",
                word = "Literacy",
                synonyms = listOf("reading skills", "proficiency", "fluency"),
                otherWords = listOf("sculpture", "geography", "chemistry")
            ),
            SynonymsGame(
                id = "23",
                category = "Education",
                word = "Pedagogy",
                synonyms = listOf("teaching methods", "instruction", "educational theory"),
                otherWords = listOf("rebellion", "competition", "regulation")
            ),
            SynonymsGame(
                id = "24",
                category = "Education",
                word = "Assessment",
                synonyms = listOf("evaluation", "appraisal", "grading"),
                otherWords = listOf("ignorance", "stagnation", "vacation")
            ),
            SynonymsGame(
                id = "25",
                category = "Education",
                word = "Scholarship",
                synonyms = listOf("grant", "funding", "bursary"),
                otherWords = listOf("penalty", "rejection", "dictatorship")
            ),
            SynonymsGame(
                id = "26",
                category = "Education",
                word = "Vocational",
                synonyms = listOf("job-related", "career-oriented", "technical"),
                otherWords = listOf("unnecessary", "recreational", "passive")
            ),
            SynonymsGame(
                id = "27",
                category = "Education",
                word = "Higher education",
                synonyms = listOf("university", "tertiary education", "advanced learning"),
                otherWords = listOf("basics", "childhood", "withdrawal")
            ),
            SynonymsGame(
                id = "28",
                category = "Education",
                word = "Compulsory",
                synonyms = listOf("mandatory", "obligatory", "required"),
                otherWords = listOf("voluntary", "optional", "flexible")
            ),
            SynonymsGame(
                id = "29",
                category = "Education",
                word = "E-learning",
                synonyms = listOf("online education", "digital learning", "virtual classes"),
                otherWords = listOf("manual labor", "farming", "archaeology")
            ),
            SynonymsGame(
                id = "30",
                category = "Education",
                word = "Tuition fees",
                synonyms = listOf("educational costs", "school fees", "academic expenses"),
                otherWords = listOf("earnings", "donations", "taxation")
            ),
            SynonymsGame(
                id = "31",
                category = "Technology",
                word = "Innovation",
                synonyms = listOf("advancement", "breakthrough", "invention"),
                otherWords = listOf("stagnation", "destruction", "withdrawal")
            ),
            SynonymsGame(
                id = "32",
                category = "Technology",
                word = "Artificial intelligence",
                synonyms = listOf("machine learning", "AI", "automation"),
                otherWords = listOf("agriculture", "emotions", "intuition")
            ),
            SynonymsGame(
                id = "33",
                category = "Technology",
                word = "Cybersecurity",
                synonyms = listOf("data protection", "digital security", "online safety"),
                otherWords = listOf("invasion", "vulnerability", "gardening")
            ),
            SynonymsGame(
                id = "34",
                category = "Technology",
                word = "Automation",
                synonyms = listOf("mechanization", "robotics", "self-operation"),
                otherWords = listOf("manual labor", "disorder", "hesitation")
            ),
            SynonymsGame(
                id = "35",
                category = "Technology",
                word = "Encryption",
                synonyms = listOf("coding", "encoding", "data protection"),
                otherWords = listOf("exposure", "openness", "transparency")
            ),
            SynonymsGame(
                id = "36",
                category = "Technology",
                word = "Big data",
                synonyms = listOf("large-scale information", "massive datasets", "analytics"),
                otherWords = listOf("small-scale", "handwritten notes", "elementary math")
            ),
            SynonymsGame(
                id = "37",
                category = "Technology",
                word = "Cloud computing",
                synonyms = listOf("online storage", "remote servers", "virtual computing"),
                otherWords = listOf("hardware", "paper documents", "landfill")
            ),
            SynonymsGame(
                id = "38",
                category = "Technology",
                word = "Quantum computing",
                synonyms = listOf("advanced computing", "quantum mechanics", "next-gen technology"),
                otherWords = listOf("analog systems", "simple arithmetic", "superstition")
            ),
            SynonymsGame(
                id = "39",
                category = "Technology",
                word = "Biotechnology",
                synonyms = listOf("genetic engineering", "bioinformatics", "life sciences"),
                otherWords = listOf("folklore", "witchcraft", "fossil fuels")
            ),
            SynonymsGame(
                id = "40",
                category = "Technology",
                word = "Virtual reality",
                synonyms = listOf("VR", "immersive experience", "simulated environment"),
                otherWords = listOf("physical space", "real-life interaction", "philosophy")
            ),
            SynonymsGame(
                id = "41",
                category = "Economy",
                word = "Inflation",
                synonyms = listOf("price rise", "economic expansion", "monetary depreciation"),
                otherWords = listOf("deflation", "stagnation", "philosophy")
            ),
            SynonymsGame(
                id = "42",
                category = "Economy",
                word = "Unemployment",
                synonyms = listOf("joblessness", "redundancy", "workforce reduction"),
                otherWords = listOf("retirement", "investment", "graduation")
            ),
            SynonymsGame(
                id = "43",
                category = "Economy",
                word = "GDP (Gross Domestic Product)",
                synonyms = listOf("economic output", "national income", "total production"),
                otherWords = listOf("pollution", "childhood", "anatomy")
            ),
            SynonymsGame(
                id = "44",
                category = "Economy",
                word = "Recession",
                synonyms = listOf("economic decline", "downturn", "depression"),
                otherWords = listOf("growth", "celebration", "inheritance")
            ),
            SynonymsGame(
                id = "45",
                category = "Economy",
                word = "Subsidies",
                synonyms = listOf("government aid", "financial assistance", "grants"),
                otherWords = listOf("penalties", "fines", "bribery")
            ),
            SynonymsGame(
                id = "46",
                category = "Economy",
                word = "Monopoly",
                synonyms = listOf("market control", "dominance", "exclusivity"),
                otherWords = listOf("democracy", "fragmentation", "decentralization")
            ),
            SynonymsGame(
                id = "47",
                category = "Economy",
                word = "Taxation",
                synonyms = listOf("levies", "duties", "government charges"),
                otherWords = listOf("exemption", "donation", "generosity")
            ),
            SynonymsGame(
                id = "48",
                category = "Economy",
                word = "Interest rates",
                synonyms = listOf("borrowing cost", "loan percentage", "lending fee"),
                otherWords = listOf("employment", "immigration", "biodiversity")
            ),
            SynonymsGame(
                id = "49",
                category = "Economy",
                word = "Stock market",
                synonyms = listOf("financial exchange", "trading platform", "equities market"),
                otherWords = listOf("agriculture", "law enforcement", "sculpting")
            ),
            SynonymsGame(
                id = "50",
                category = "Economy",
                word = "Cryptocurrency",
                synonyms = listOf("digital currency", "blockchain-based money", "decentralized finance"),
                otherWords = listOf("banknotes", "antique coins", "pottery")
            ),
            SynonymsGame(
                id = "51",
                category = "Health",
                word = "Pandemic",
                synonyms = listOf("global outbreak", "epidemic", "disease spread"),
                otherWords = listOf("meditation", "prosperity", "architecture")
            ),
            SynonymsGame(
                id = "52",
                category = "Health",
                word = "Vaccination",
                synonyms = listOf("immunization", "inoculation", "preventive medicine"),
                otherWords = listOf("infection", "neglect", "agriculture")
            ),
            SynonymsGame(
                id = "53",
                category = "Health",
                word = "Mental health",
                synonyms = listOf("psychological well-being", "emotional stability", "cognitive health"),
                otherWords = listOf("digestion", "geography", "aesthetics")
            ),
            SynonymsGame(
                id = "54",
                category = "Health",
                word = "Obesity",
                synonyms = listOf("overweight", "excessive weight", "high BMI"),
                otherWords = listOf("malnutrition", "underweight", "dehydration")
            ),
            SynonymsGame(
                id = "55",
                category = "Health",
                word = "Hygiene",
                synonyms = listOf("cleanliness", "sanitation", "health practices"),
                otherWords = listOf("pollution", "chaos", "disorganization")
            ),
            SynonymsGame(
                id = "56",
                category = "Health",
                word = "Nutrition",
                synonyms = listOf("diet", "nourishment", "healthy eating"),
                otherWords = listOf("fasting", "starvation", "mechanics")
            ),
            SynonymsGame(
                id = "57",
                category = "Health",
                word = "Physical fitness",
                synonyms = listOf("exercise", "stamina", "athleticism"),
                otherWords = listOf("laziness", "procrastination", "brainstorming")
            ),
            SynonymsGame(
                id = "58",
                category = "Health",
                word = "Healthcare system",
                synonyms = listOf("medical services", "public health", "hospitals"),
                otherWords = listOf("entertainment", "military", "exploration")
            ),
            SynonymsGame(
                id = "59",
                category = "Health",
                word = "Chronic disease",
                synonyms = listOf("long-term illness", "persistent condition", "non-communicable disease"),
                otherWords = listOf("short-term", "accident", "repair")
            ),
            SynonymsGame(
                id = "60",
                category = "Health",
                word = "Alternative medicine",
                synonyms = listOf("herbal remedies", "holistic treatment", "naturopathy"),
                otherWords = listOf("surgery", "pharmaceuticals", "robotics")
            ),
            SynonymsGame(
                id = "61",
                category = "Crime & Law",
                word = "Legislation",
                synonyms = listOf("laws", "legal framework", "regulations"),
                otherWords = listOf("myths", "poetry", "election")
            ),
            SynonymsGame(
                id = "62",
                category = "Crime & Law",
                word = "Jurisdiction",
                synonyms = listOf("authority", "legal control", "governance"),
                otherWords = listOf("coincidence", "harmony", "astronomy")
            ),
            SynonymsGame(
                id = "63",
                category = "Crime & Law",
                word = "Criminal offense",
                synonyms = listOf("crime", "felony", "lawbreaking"),
                otherWords = listOf("charity", "donation", "employment")
            ),
            SynonymsGame(
                id = "64",
                category = "Crime & Law",
                word = "Rehabilitation",
                synonyms = listOf("reintegration", "recovery", "reformation"),
                otherWords = listOf("condemnation", "exclusion", "betrayal")
            ),
            SynonymsGame(
                id = "65",
                category = "Crime & Law",
                word = "Forensic science",
                synonyms = listOf("criminal investigation", "forensic analysis", "legal evidence"),
                otherWords = listOf("literature", "interior design", "recreation")
            ),
            SynonymsGame(
                id = "66",
                category = "Crime & Law",
                word = "Corruption",
                synonyms = listOf("bribery", "fraud", "dishonesty"),
                otherWords = listOf("justice", "integrity", "morality")
            ),
            SynonymsGame(
                id = "67",
                category = "Crime & Law",
                word = "Cybercrime",
                synonyms = listOf("hacking", "online fraud", "digital crime"),
                otherWords = listOf("charity", "physical training", "landscaping")
            ),
            SynonymsGame(
                id = "68",
                category = "Crime & Law",
                word = "Surveillance",
                synonyms = listOf("monitoring", "tracking", "security oversight"),
                otherWords = listOf("invisibility", "wilderness", "architecture")
            ),
            SynonymsGame(
                id = "69",
                category = "Crime & Law",
                word = "Imprisonment",
                synonyms = listOf("incarceration", "confinement", "detention"),
                otherWords = listOf("freedom", "migration", "promotion")
            ),
            SynonymsGame(
                id = "70",
                category = "Crime & Law",
                word = "Human rights",
                synonyms = listOf("civil liberties", "fundamental freedoms", "legal entitlements"),
                otherWords = listOf("punishment", "dictatorship", "trade")
            ),
            SynonymsGame(
                id = "71",
                category = "Transport",
                word = "Infrastructure",
                synonyms = listOf("transport network", "public facilities", "urban development"),
                otherWords = listOf("ethics", "culture", "solitude")
            ),
            SynonymsGame(
                id = "72",
                category = "Transport",
                word = "Public transport",
                synonyms = listOf("mass transit", "buses and trains", "commuter systems"),
                otherWords = listOf("aviation", "pilgrimage", "deforestation")
            ),
            SynonymsGame(
                id = "73",
                category = "Transport",
                word = "Congestion",
                synonyms = listOf("traffic jam", "overcrowding", "road blockage"),
                otherWords = listOf("relaxation", "emptiness", "farming")
            ),
            SynonymsGame(
                id = "74",
                category = "Transport",
                word = "Sustainability in transport",
                synonyms = listOf("eco-friendly travel", "green transport", "fuel efficiency"),
                otherWords = listOf("pollution", "wastefulness", "disorder")
            ),
            SynonymsGame(
                id = "75",
                category = "Transport",
                word = "Autonomous vehicles",
                synonyms = listOf("self-driving cars", "AI-powered transport", "automated vehicles"),
                otherWords = listOf("handicrafts", "textiles", "botany")
            )
        )

        questions.forEach { question ->
            database.child("games").child("synonyms").child(question.id).setValue(question)
        }
    }
}
