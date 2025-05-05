package com.example.support.feature.essaybuilder.presentation.repository

import com.example.support.core.util.GameManager
import com.example.support.core.util.GameResultManager
import com.example.support.core.util.ResultCore
import com.example.support.feature.essaybuilder.presentation.data.EssayBuilderGame
import javax.inject.Inject

class EssayBuilderGameManager @Inject constructor(
    private val repository: EssayBuilderGameRepository,
    private val resultManager: GameResultManager
) : GameManager {
    private var shuffledIds = mutableListOf<String>()
    private var currentIndex = 0

    override suspend fun loadShuffledIdsIfNeeded(): ResultCore<Unit> {
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

    override suspend fun getNextQuestion(): ResultCore<EssayBuilderGame> {
        if (currentIndex >= shuffledIds.size) {
            return ResultCore.Failure("No more questions")
        }
        val nextId = shuffledIds[currentIndex++]
        return repository.getQuestionById(nextId)
    }

    override fun saveScore(score: Int) {
        resultManager.saveResult(score)
    }

    override fun reset() {
        shuffledIds.clear()
        currentIndex = 0
    }
}