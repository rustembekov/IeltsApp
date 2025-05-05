package com.example.support.feature.essaybuilder.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseGameViewModel
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.util.HapticFeedbackManager
import com.example.support.core.util.ResultCore
import com.example.support.core.util.TimerManager
import com.example.support.feature.essaybuilder.model.EssayBuilderEvent
import com.example.support.feature.essaybuilder.model.EssayBuilderState
import com.example.support.feature.essaybuilder.model.EssayBuilderState.EssayBuilderResult
import com.example.support.feature.essaybuilder.model.EssayBuilderState.Token
import com.example.support.feature.essaybuilder.presentation.data.EssayBuilderGame
import com.example.support.feature.essaybuilder.presentation.repository.EssayBuilderGameManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EssayBuilderViewModel @Inject constructor(
    navigator: Navigator,
    private val gameManager: EssayBuilderGameManager,
    private val timerManager: TimerManager,
    private val hapticFeedbackManager: HapticFeedbackManager
) : BaseGameViewModel<EssayBuilderState, EssayBuilderEvent>(EssayBuilderState(), navigator),
    EssayBuilderController {

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

    override fun onEvent(event: EssayBuilderEvent) {
        when (event) {
            is EssayBuilderEvent.StartGame -> {
                if (!uiState.value.hasStarted) {
                    updateState(uiState.value.copy(hasStarted = true))
                    startGame()
                    timerManager.startTimer(viewModelScope, uiState.value.timer)
                }
            }

            is EssayBuilderEvent.AnswerQuestion -> {
                checkAnswer()
            }
        }
    }

    private fun startGame() {
        updateState(uiState.value.copy(result = EssayBuilderResult.Loading))
        viewModelScope.launch {
            when (val init = gameManager.loadShuffledIdsIfNeeded()) {
                is ResultCore.Failure -> {
                    updateState(uiState.value.copy(result = EssayBuilderResult.Error(init.message)))
                    return@launch
                }

                is ResultCore.Success -> loadNextQuestion()
            }
        }
    }

    private fun loadNextQuestion() {
        viewModelScope.launch {
            when (val result: ResultCore<EssayBuilderGame> = gameManager.getNextQuestion()) {
                is ResultCore.Success -> {
                    val game = result.data

                    val parts = game.questionParts.map {
                        when (it.type) {
                            "text" -> EssayBuilderState.Part.Text(it.value ?: "")
                            "blank" -> EssayBuilderState.Part.Blank(it.index ?: 0)
                            else -> throw IllegalArgumentException("Unknown part type: ${it.type}")
                        }
                    }

                    val currentBlanks = List(game.correctAnswers.size) { null }

                    // Process parts into tokens
                    val tokens = processPartsIntoTokens(parts)

                    updateState(
                        uiState.value.copy(
                            questionParts = parts,
                            options = game.options.map { EssayBuilderState.OptionUiModel(word = it) },
                            currentBlanks = currentBlanks,
                            tokens = tokens,
                            result = EssayBuilderResult.Success,
                            correctAnswers = game.correctAnswers
                        )
                    )
                }

                is ResultCore.Failure -> {
                    updateState(uiState.value.copy(result = EssayBuilderResult.Error(result.message)))
                }
            }
        }
    }

    private fun processPartsIntoTokens(parts: List<EssayBuilderState.Part>): List<Token> {
        val tokens = mutableListOf<Token>()

        // Process parts into tokens (words, spaces, and blanks)
        parts.forEach { part ->
            when (part) {
                is EssayBuilderState.Part.Text -> {
                    // Split text into words and spaces
                    var remaining = part.text
                    var lastWasSpace = false

                    while (remaining.isNotEmpty()) {
                        when {
                            remaining.startsWith(" ") -> {
                                tokens.add(Token(isSpace = true, content = " "))
                                remaining = remaining.substring(1)
                                lastWasSpace = true
                            }
                            else -> {
                                // Find next space or end of string
                                val nextSpaceIndex = remaining.indexOf(' ').let {
                                    if (it == -1) remaining.length else it
                                }
                                val word = remaining.substring(0, nextSpaceIndex)
                                tokens.add(Token(content = word))
                                remaining = remaining.substring(nextSpaceIndex)
                                lastWasSpace = false
                            }
                        }
                    }
                }
                is EssayBuilderState.Part.Blank -> {
                    tokens.add(Token(isBlank = true, blankIndex = part.index))
                }
            }
        }

        return tokens
    }

    private fun checkAnswer() {
        val currentState = uiState.value
        val correctAnswers = currentState.correctAnswers.toSet()

        // Update each blank to show if it's correct or incorrect
        val updatedBlanks = currentState.currentBlanks.map { blank ->
            blank?.copy(
                isCorrect = correctAnswers.contains(blank.word),
                isSelected = true  // Mark as selected to show the correct/incorrect color
            )
        }

        val selectedAnswers = updatedBlanks.mapNotNull { it?.word }.toSet()
        val numCorrectSelected = selectedAnswers.count { correctAnswers.contains(it) }

        val scoreIncrement = numCorrectSelected * 10

        updateState(currentState.copy(
            currentBlanks = updatedBlanks,
            score = currentState.score + scoreIncrement,
            isAnswerChecked = true
        ))

        timerManager.pauseTimer()

        // Delay before moving to next question
        viewModelScope.launch {
            delay(1500L)

            // Reset state and load next question
            loadNextQuestion()
            timerManager.resumeTimer()
            updateState(uiState.value.copy(isAnswerChecked = false))
        }

        // Give haptic feedback for incorrect answers
        if (selectedAnswers != correctAnswers) {
            hapticFeedbackManager.vibrate(200)
        }
    }

    override fun onWordClick(word: String) {
        val state = uiState.value

        // If answers have been checked, ignore word clicks
        if (state.isAnswerChecked) {
            return
        }

        val isSelected = state.selectedWord == word
        val newOptions = state.options.map {
            when {
                it.word == word -> it.copy(isSelected = !isSelected)
                else -> it.copy(isSelected = false)
            }
        }

        updateState(
            state.copy(
                options = newOptions,
                selectedWord = if (isSelected) null else word
            )
        )
    }

    override fun onBlankClick(index: Int) {
        val state = uiState.value

        // If answers have been checked, ignore blank clicks
        if (state.isAnswerChecked) {
            return
        }

        val selectedWord = state.selectedWord
        val currentBlanks = state.currentBlanks.toMutableList()

        val removed = currentBlanks[index]
        if (removed != null) {
            // Remove word from blank
            val updatedOptions = state.options.map {
                if (it.word == removed.word) it.copy(isUsed = false) else it
            }
            currentBlanks[index] = null

            updateState(
                state.copy(
                    currentBlanks = currentBlanks,
                    options = updatedOptions
                )
            )
        } else if (selectedWord != null) {
            // Add selected word to blank
            if (state.options.any { it.word == selectedWord && !it.isUsed }) {
                // Create a new blank model with default state (not showing correct/incorrect yet)
                currentBlanks[index] = EssayBuilderState.BlanksUiModel(
                    word = selectedWord,
                    isSelected = false,  // Not showing correct/incorrect color yet
                    isCorrect = false    // Will be determined during checkAnswer
                )

                val updatedOptions = state.options.map {
                    if (it.word == selectedWord) it.copy(isUsed = true, isSelected = false) else it
                }

                updateState(
                    state.copy(
                        currentBlanks = currentBlanks,
                        options = updatedOptions,
                        selectedWord = null
                    )
                )
            }
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

    override fun onCleared() {
        super.onCleared()
        timerManager.stopTimer()
    }

    private fun reset() {
        timerManager.resetTimer(viewModelScope)
        gameManager.reset()
        updateState(
            EssayBuilderState()
        )
    }
}