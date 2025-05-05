package com.example.support.core.util

/**
 * Common interface for all game managers
 */
interface GameManager {
    suspend fun loadShuffledIdsIfNeeded(): com.example.support.core.util.ResultCore<Unit>
    suspend fun getNextQuestion(): com.example.support.core.util.ResultCore<*>
    fun saveScore(score: Int)
    fun reset()
}