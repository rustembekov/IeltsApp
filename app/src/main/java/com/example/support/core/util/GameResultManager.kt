package com.example.support.core.util

import javax.inject.Inject
import javax.inject.Singleton

interface GameResultManager {
    fun saveResult(score: Int)
    fun getResult(): Int
}

@Singleton
class GameResultManagerImpl @Inject constructor(): GameResultManager {
    private var score: Int = 0

    override fun saveResult(score: Int) {
        this.score = score
    }

    override fun getResult(): Int = score
}
