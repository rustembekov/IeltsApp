package com.example.support.core.util.timer

import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseGameViewModel
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.ui.views.pauseDialog.model.PauseState
import com.example.support.core.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Controller responsible for managing game timer logic consistently across different games
 */
@Singleton
class GameTimerController @Inject constructor(
    private val timerManager: TimerManager
) {
    /**
     * Initializes the timer observation for a game
     *
     * @param viewModel The game's ViewModel
     * @param updateTimerState Function to update the timer state in the ViewModel
     * @param onTimeExpired Function to handle timer expiration
     */
    fun <S : PauseState, E : Any> initializeTimer(
        viewModel: BaseGameViewModel<S, E>,
        updateTimerState: (Int) -> Unit,
        onTimeExpired: (Int) -> Unit
    ) {
        viewModel.timerManager.timerFlow
            .onEach { time ->
                time?.let {
                    updateTimerState(it)

                    if (it == 0 && viewModel.isGameStarted() ) {
                        val score = viewModel.getCurrentScore()
                        onTimeExpired(score)
                    }
                }
            }
            .launchIn(viewModel.viewModelScope)
    }


    /**
     * Starts the game timer
     *
     * @param scope CoroutineScope to launch the timer in
     * @param duration Timer duration in seconds
     */
    fun startGameTimer(scope: CoroutineScope, duration: Int = Constants.GAME_TIMER_DURATION) {
        timerManager.startTimer(scope, duration)
    }

    /**
     * Resets the game timer
     *
     * @param scope CoroutineScope to launch the timer in
     */
    fun resetGameTimer(scope: CoroutineScope) {
        timerManager.resetTimer(scope)
    }

    /**
     * Stops the game timer
     */
    fun stopGameTimer() {
        timerManager.stopTimer()
    }

    /**
     * Pauses the game timer
     */
    fun pauseGameTimer() {
        timerManager.pauseTimer()
    }

    /**
     * Resumes the game timer
     */
    fun resumeGameTimer() {
        timerManager.resumeTimer()
    }

    /**
     * Handles a game completion event by navigating to the completion screen
     * and performing necessary cleanup
     *
     * @param score Final game score
     * @param navigator Navigator to handle routing
     * @param gameManager Game manager to save score
     * @param resetFunction Function to reset the game state
     */
    suspend fun handleGameCompletion(
        score: Int,
        navigator: Navigator,
        gameManager: Any,
        resetFunction: () -> Unit
    ) {
        // Cast gameManager to the appropriate interface if needed
        // This uses reflection to call saveScore on any game manager type
        try {
            val saveScoreMethod = gameManager.javaClass.getMethod("saveScore", Int::class.java)
            saveScoreMethod.invoke(gameManager, score)
        } catch (e: Exception) {
            // Log error or handle gracefully
        }

        navigator.navigate(NavigationEvent.Navigate(NavigationItem.GameCompletion.route))

        // Reset after a short delay to allow for navigation
        val scope = CoroutineScope(kotlinx.coroutines.Dispatchers.Main)
        scope.launch {
            delay(300L)
            resetFunction()
        }
    }
}