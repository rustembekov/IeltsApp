package com.example.support.feature.factopinion.presentation.repository

import com.example.support.core.util.GameResultManager
import com.example.support.core.util.ResultCore
import com.example.support.feature.factopinion.presentation.data.FactOpinionGame
import javax.inject.Inject

class FactOpinionGameManager @Inject constructor(
    private val repository: FactOpinionGameRepository,
    private val resultManager: GameResultManager
    ) {
    private var shuffledIds = mutableListOf<String>()
    private var currentIndex = 0

    suspend fun loadShuffledIdsIfNeeded(): ResultCore<Unit> {
        if (shuffledIds.isEmpty()) {
            return when (val result = repository.getAllQuestionIds()) {
                is ResultCore.Success -> {
                    shuffledIds = result.data.shuffled().toMutableList()
                    currentIndex = 0
                    ResultCore.Success(Unit)
                }
                is ResultCore.Failure -> result
            }
        }
        return ResultCore.Success(Unit)
    }

    suspend fun getNextQuestion(): ResultCore<FactOpinionGame> {
        if (currentIndex >= shuffledIds.size) {
            return ResultCore.Failure("No more questions")
        }
        val nextId = shuffledIds[currentIndex++]
        return repository.getQuestionById(nextId)
    }

    fun saveScore(score: Int) {
        resultManager.saveResult(score)
    }

    fun reset() {
        shuffledIds.clear()
        currentIndex = 0
    }
}
