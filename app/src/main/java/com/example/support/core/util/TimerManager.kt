package com.example.support.core.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TimerManager @Inject constructor() {

    private var countdownJob: Job? = null
    private val _timerFlow = MutableStateFlow<Int?>(null)
    val timerFlow: StateFlow<Int?> = _timerFlow.asStateFlow()

    private var isPaused = false
    private var lastDuration: Int = 20

    fun startTimer(scope: CoroutineScope, duration: Int = 20, onFinished: () -> Unit = {}) {
        countdownJob?.cancel()
        lastDuration = duration

        countdownJob = scope.launch {
            for (time in duration downTo 0) {
                _timerFlow.value = time
                delay(1000L)

                while (isPaused) {
                    delay(100L)
                }
            }
            onFinished()
        }
    }

    fun resetTimer(scope: CoroutineScope, onFinished: () -> Unit = {}) {
        startTimer(scope, lastDuration, onFinished)
    }

    fun stopTimer() {
        countdownJob?.cancel()
        countdownJob = null
        _timerFlow.value = null
    }

    fun pauseTimer() {
        isPaused = true
    }

    fun resumeTimer() {
        isPaused = false
    }

    fun isActive(): Boolean {
        return countdownJob?.isActive == true
    }
}


