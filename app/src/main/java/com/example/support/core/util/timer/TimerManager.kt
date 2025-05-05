package com.example.support.core.util.timer

import com.example.support.core.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages timer functionality across game features
 * Provides consistent timer behavior with pause/resume capabilities
 */
@Singleton
class TimerManager @Inject constructor() {

    private var countdownJob: Job? = null
    private val _timerFlow = MutableStateFlow<Int?>(null)
    val timerFlow: StateFlow<Int?> = _timerFlow.asStateFlow()

    private var isPaused = false
    private var lastDuration: Int = Constants.GAME_TIMER_DURATION
    private var currentTime: Int = 0

    /**
     * Starts a new timer with the specified duration
     *
     * @param scope CoroutineScope to launch the timer in
     * @param duration Initial duration in seconds
     * @param onFinished Optional callback when timer reaches zero
     */
    fun startTimer(scope: CoroutineScope, duration: Int = Constants.GAME_TIMER_DURATION, onFinished: () -> Unit = {}) {
        countdownJob?.cancel()
        lastDuration = duration
        currentTime = duration
        isPaused = false

        _timerFlow.value = currentTime

        countdownJob = scope.launch {
            for (time in duration downTo 0) {
                // Immediately set the initial value to avoid delay on first render
                if (time == duration) {
                    _timerFlow.value = time
                }

                delay(1000L)

                // Check pause state continuously
                while (isPaused) {
                    delay(100L)
                }

                currentTime = time - 1
                _timerFlow.value = currentTime

                // When reaching zero, trigger callback
                if (currentTime <= 0) {
                    onFinished()
                    break
                }
            }
        }
    }

    /**
     * Resets the timer to the last known duration
     *
     * @param scope CoroutineScope to launch the timer in
     * @param onFinished Optional callback when timer reaches zero
     */
    fun resetTimer(scope: CoroutineScope, onFinished: () -> Unit = {}) {
        startTimer(scope, lastDuration, onFinished)
    }

    /**
     * Stops the timer completely and clears the value
     */
    fun stopTimer() {
        countdownJob?.cancel()
        countdownJob = null
        _timerFlow.value = null
        isPaused = false
    }

    /**
     * Pauses the timer at current value
     */
    fun pauseTimer() {
        isPaused = true
    }

    /**
     * Resumes a paused timer
     */
    fun resumeTimer() {
        isPaused = false
    }

    /**
     * Returns whether the timer is currently active
     */
    fun isActive(): Boolean {
        return countdownJob?.isActive == true && !isPaused
    }

    /**
     * Returns whether the timer is currently paused
     */
    fun isPaused(): Boolean {
        return isPaused
    }

    /**
     * Gets the current timer value
     */
    fun getCurrentTime(): Int {
        return currentTime
    }
}