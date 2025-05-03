package com.example.support.feature.keywordscheck.presentation.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseGameViewModel
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.util.Constants
import com.example.support.core.util.HapticFeedbackManager
import com.example.support.core.util.ResultCore
import com.example.support.core.util.TimerManager
import com.example.support.feature.keywordscheck.model.KeywordWord
import com.example.support.feature.keywordscheck.model.KeywordsCheckEvent
import com.example.support.feature.keywordscheck.model.KeywordsCheckResult
import com.example.support.feature.keywordscheck.model.KeywordsCheckState
import com.example.support.feature.keywordscheck.presentation.repository.KeywordsCheckGameManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordsCheckViewModel @Inject constructor(
    navigator: Navigator,
    private val timerManager: TimerManager,
    private val hapticFeedbackManager: HapticFeedbackManager,
    private val gameManager: KeywordsCheckGameManager
) : BaseGameViewModel<KeywordsCheckState, KeywordsCheckEvent>(KeywordsCheckState(), navigator),
    KeywordsCheckController {

    init {
        timerManager.timerFlow
            .onEach { time ->
                time?.let {
                    updateState(uiState.value.copy(timer = it))

                    if (it == 0 && uiState.value.hasStarted) {
                        val score = uiState.value.score
                        gameManager.saveScore(score)
                        navigator.navigate(NavigationEvent.Navigate(NavigationItem.GameCompletion.route))
                        delay(300)
                        reset()
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onEvent(event: KeywordsCheckEvent) {
        when (event) {
            is KeywordsCheckEvent.StartGame -> {
                if (!uiState.value.hasStarted) {
                    updateState(uiState.value.copy(hasStarted = true))
                    startGame()
                    timerManager.startTimer(viewModelScope, Constants.GAME_TIMER_DURATION)
                }
            }

            is KeywordsCheckEvent.AnswerQuestion -> {
                checkAnswer()
            }
        }
    }

    private fun startGame() {
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

    private fun loadNextQuestion() {
        viewModelScope.launch {
            when (val result = gameManager.getNextQuestion()) {
                is ResultCore.Success -> {
                    val computedMax = result.data.answers.sumOf { it.split(" ").size }
                    Log.d("KeywordsCheckEvent", "Current Question: " + result.data.text)

                    Log.d("KeywordsCheckEvent", "Correct Answer: " + result.data.answers)
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

        val selectedCorrectWords = mutableSetOf<String>()
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



    override fun onPauseClicked() {
        super.onPauseClicked()
        timerManager.pauseTimer()
    }

    override fun onResumePauseDialog() {
        super.onResumePauseDialog()
        timerManager.resumeTimer()
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
        Log.d("KeywordsCheckEvent", "Selected Answers: " + updated.joinToString { it.text })
    }



    private fun reset() {
        timerManager.resetTimer(viewModelScope)
        gameManager.reset()
        updateState(
            KeywordsCheckState()
        )
    }
}