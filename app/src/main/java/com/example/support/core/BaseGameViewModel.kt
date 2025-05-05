package com.example.support.core

import androidx.lifecycle.viewModelScope
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.ui.views.pauseDialog.model.PauseState
import com.example.support.core.util.GameManager
import com.example.support.core.util.timer.GameTimerController
import com.example.support.core.util.timer.TimerManager
import kotlinx.coroutines.launch

/**
 * Base ViewModel for all games with common functionality
 */
abstract class BaseGameViewModel<S : PauseState, E : Any>(
    initialState: S,
    protected val navigator: Navigator,
    val timerManager: TimerManager,
    private val gameTimerController: GameTimerController
) : BaseViewModel<S, E>(initialState) {

    init {
        initializeTimerObservation()
    }

    /**
     * Set up timer observation for this game
     */
    private fun initializeTimerObservation() {
        gameTimerController.initializeTimer(
            viewModel = this,
            updateTimerState = { time -> updateState(uiState.value.copyWithTimer(time) as S) },
            onTimeExpired = { score -> handleTimeExpired(score) }
        )
    }

    /**
     * Handle timer expiration
     */
    abstract fun handleTimeExpired(score: Int)

    /**
     * Start the game
     */
    protected fun startGame(duration: Int = com.example.support.core.util.Constants.GAME_TIMER_DURATION) {
        if (!isGameStarted()) {
            updateState(uiState.value.copyWithGameStarted(true) as S)
            initializeGame()
            gameTimerController.startGameTimer(viewModelScope, duration)
        }
    }

    /**
     * Initialize game-specific resources
     */
    abstract fun initializeGame()

    /**
     * Reset the game state
     */
    protected fun resetGame() {
        gameTimerController.resetGameTimer(viewModelScope)
        getGameManager()?.reset()
        updateState(createInitialState())
    }

    /**
     * Get the game-specific manager
     */
    abstract  fun getGameManager(): GameManager?

    /**
     * Get the current score
     */
    abstract fun getCurrentScore(): Int

    /**
     * Check if game has started
     */
    abstract fun isGameStarted(): Boolean

    /**
     * Create the initial state for reset
     */
    abstract fun createInitialState(): S

    /**
     * Pause the game timer
     */
    open fun onPauseClicked() {
        gameTimerController.pauseGameTimer()
        updateState(uiState.value.copyPauseState(true) as S)
    }

    /**
     * Resume the game timer
     */
    open fun onResumePauseDialog() {
        gameTimerController.resumeGameTimer()
        updateState(uiState.value.copyPauseState(false) as S)
    }

    /**
     * Quit the current game
     */
    fun onQuitGame() {
        viewModelScope.launch {
            gameTimerController.stopGameTimer()
            navigator.navigate(NavigationEvent.Navigate(NavigationItem.Home.route))
        }
    }

    /**
     * Clean up resources when ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        gameTimerController.stopGameTimer()
    }
}