package com.example.support.feature.gamecompletion.presentation.repository

import com.example.support.core.util.GameResultManager
import com.example.support.core.util.ResultCore
import com.example.support.core.data.UserRepository
import com.example.support.feature.gamecompletion.presentation.domain.GameCompletionData
import javax.inject.Inject

class GameCompletionManager @Inject constructor(
    private val resultManager: GameResultManager,
    private val userManager: UserRepository,
    private val gameCompletionRepository: GameCompletionRepository

) {
    suspend fun completeGame(): ResultCore<GameCompletionData> {
        val earnedScore = resultManager.getResult()
        val userId =  userManager.getCurrentUserId()
        return gameCompletionRepository.processGameResult(userId = userId.toString(), newScore = earnedScore)
    }

     fun getPreviousGameRoute(): String = gameCompletionRepository.getPreviousGameRoute()
}