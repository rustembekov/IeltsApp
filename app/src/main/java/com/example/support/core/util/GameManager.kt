package com.example.support.core.util

/**
 * Common interface for all game managers
 */
interface GameManager {
    suspend fun loadShuffledIdsIfNeeded(): ResultCore<Unit>
    suspend fun getNextQuestion(): ResultCore<*>
    fun saveScore(score: Int)
    fun reset()
}