package com.example.support.feature.gamecompletion.presentation.repository

import com.example.support.core.util.GameResultManager
import com.example.support.core.util.ResultCore
import com.example.support.core.data.UserRepository
import com.example.support.feature.gamecompletion.presentation.domain.GameResultDelta
import javax.inject.Inject

class GameCompletionManager @Inject constructor(
    private val resultManager: GameResultManager,
    private val userManager: UserRepository,
    private val gameCompletionRepository: GameCompletionRepository

) {
    suspend fun completeGame(): ResultCore<GameResultDelta> {
        val earnedScore = resultManager.getResult()
        val userId =  userManager.getUserId()
        return gameCompletionRepository.processGameResult(userId = userId.toString(), newScore = earnedScore)
    }

}