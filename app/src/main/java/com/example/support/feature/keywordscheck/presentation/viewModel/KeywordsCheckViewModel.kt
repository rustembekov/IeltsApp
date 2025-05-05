package com.example.support.feature.keywordscheck.presentation.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseGameViewModel
import com.example.support.core.data.GamePreferences
import com.example.support.core.navigation.Navigator
import com.example.support.core.util.Constants
import com.example.support.core.util.GameManager
import com.example.support.core.util.HapticFeedbackManager
import com.example.support.core.util.ResultCore
import com.example.support.core.util.timer.GameTimerController
import com.example.support.core.util.timer.TimerManager
import com.example.support.feature.keywordscheck.model.KeywordWord
import com.example.support.feature.keywordscheck.model.KeywordsCheckEvent
import com.example.support.feature.keywordscheck.model.KeywordsCheckResult
import com.example.support.feature.keywordscheck.model.KeywordsCheckState
import com.example.support.feature.keywordscheck.presentation.repository.KeywordsCheckGameManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordsCheckViewModel @Inject constructor(
    navigator: Navigator,
    timerManager: TimerManager,
    gameTimerController: GameTimerController,
    private val gameManager: KeywordsCheckGameManager,
    private val hapticFeedbackManager: HapticFeedbackManager,
    private val gamePreferences: GamePreferences
) : BaseGameViewModel<KeywordsCheckState, KeywordsCheckEvent>(
    KeywordsCheckState(),
    navigator,
    timerManager,
    gameTimerController
), KeywordsCheckController {
    override fun onEvent(event: KeywordsCheckEvent) {
        when (event) {
            is KeywordsCheckEvent.StartGame -> {
                startGame()
            }

            is KeywordsCheckEvent.AnswerQuestion -> {
                checkAnswer()
            }
        }
    }

    override fun initializeGame() {
        updateState(uiState.value.copy(result = KeywordsCheckResult.Loading))
        viewModelScope.launch {
            when (val init = gameManager.loadShuffledIdsIfNeeded()) {
                is ResultCore.Failure -> {
                    updateState(uiState.value.copy(result = KeywordsCheckResult.Error(init.message)))
                    return@launch
                }
                is ResultCore.Success -> loadNextQuestion()
            }
        }
    }

    override fun getGameManager(): GameManager = gameManager

    private fun loadNextQuestion() {
        viewModelScope.launch {
            when (val result = gameManager.getNextQuestion()) {
                is ResultCore.Success -> {
                    val computedMax = result.data.answers.sumOf { it.split(" ").size }
                    updateState(
                        uiState.value.copy(
                            currentQuestion = result.data.text,
                            correctAnswers = result.data.answers,
                            result = KeywordsCheckResult.Success,
                            selectedWords = emptyList(),
                            maxSelectableWords = computedMax
                        )
                    )
                }

                is ResultCore.Failure -> {
                    updateState(uiState.value.copy(result = KeywordsCheckResult.Error(result.message)))
                }
            }
        }
    }

    private fun checkAnswer() {
        val currentState = uiState.value
        val correctPhrases = currentState.correctAnswers
        val selectedWordSet = currentState.selectedWords.map { it.text }.toSet()
        val questionWords = currentState.currentQuestion
            .replace(Regex("[.,;]"), "")
            .split(" ")

        val matchedPhraseWords = mutableSetOf<String>()

        for (phrase in correctPhrases) {
            val phraseWords = phrase.split(" ")

            for (i in 0..(questionWords.size - phraseWords.size)) {
                val window = questionWords.subList(i, i + phraseWords.size)
                if (window == phraseWords && window.all { selectedWordSet.contains(it) }) {
                    matchedPhraseWords.addAll(window)
                }
            }
        }

        val updatedWords = currentState.selectedWords.map { word ->
            if (matchedPhraseWords.contains(word.text)) {
                word.copy(isCorrect = true)
            } else {
                word.copy(isCorrect = false)
            }
        }

        val scoreIncrement = matchedPhraseWords.size * 10

        updateState(
            currentState.copy(
                selectedWords = updatedWords,
                score = currentState.score + scoreIncrement
            )
        )

        timerManager.pauseTimer()

        viewModelScope.launch {
            delay(1500L)
            loadNextQuestion()
            timerManager.resumeTimer()
        }

        if (matchedPhraseWords.isEmpty()) {
            hapticFeedbackManager.vibrate(200)
        }
    }

    override fun handleTimeExpired(score: Int) {
        gameManager.saveScore(score)
        gamePreferences.setLastPlayedGame(Constants.KEYWORDS_CHECK_GAME)

        viewModelScope.launch {
            navigator.navigate(com.example.support.core.navigation.model.NavigationEvent.Navigate(
                com.example.support.core.navigation.model.NavigationItem.GameCompletion.route
            ))
            delay(300)
            resetGame()
        }
    }

    override fun toggleWordSelection(word: String) {
        val currentState = uiState.value
        val updated = currentState.selectedWords.toMutableList()
        val exists = updated.indexOfFirst { it.text == word }

        if (exists >= 0) {
            updated.removeAt(exists)
        } else {
            if (updated.size < currentState.maxSelectableWords) {
                updated.add(
                    KeywordWord(
                        text = word,
                        isSelected = true
                    )
                )
            } else {
                return
            }
        }

        updateState(currentState.copy(selectedWords = updated))
    }

    override fun getCurrentScore(): Int = uiState.value.score

    override fun isGameStarted(): Boolean = uiState.value.hasStarted

    override fun createInitialState(): KeywordsCheckState = KeywordsCheckState()
}