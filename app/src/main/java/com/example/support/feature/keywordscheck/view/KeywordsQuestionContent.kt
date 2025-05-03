@file:OptIn(ExperimentalLayoutApi::class)

package com.example.support.feature.keywordscheck.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.feature.keywordscheck.model.KeywordWord
import com.example.support.feature.keywordscheck.model.KeywordsCheckEvent
import com.example.support.feature.keywordscheck.model.KeywordsCheckState
import com.example.support.feature.keywordscheck.presentation.viewModel.KeywordsCheckController

@Composable
fun KeywordsQuestionContent(
    modifier: Modifier = Modifier,
    controller: KeywordsCheckController,
    state: KeywordsCheckState
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {

        KeywordsQuestionCard(
            controller = controller,
            state = state
        )
    }
}

@Composable
private fun KeywordsQuestionCard(
    state: KeywordsCheckState,
    controller: KeywordsCheckController
) {
    val selectedWordsMap = state.selectedWords.associateBy { it.text }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(32.dp))
            .background(AppTheme.colors.homeItemBackground)
            .padding(24.dp)
            .padding(top = 24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                state.currentQuestion.split(" ").forEach { rawWord ->
                    val cleaned = rawWord.trimEnd('.', ',', ';')
                    val selected = selectedWordsMap[cleaned]

                    val bgColor = when {
                        selected?.isCorrect == true -> AppTheme.colors.synonymCorrectBackground
                        selected?.isCorrect == false ->  AppTheme.colors.synonymIncorrectBackground
                        selected?.isSelected == true -> AppTheme.colors.synonymSelectedBackground
                        else -> Color.Transparent
                    }

                    Text(
                        text = rawWord,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(bgColor)
                            .clickable(enabled = selected != null || state.selectedWords.size < state.maxSelectableWords) {
                                controller.toggleWordSelection(cleaned)
                            }
                            .padding(horizontal = 6.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun KeywordsQuestionContentPreview() {
    val mockController = object : KeywordsCheckController {
        override fun onEvent(event: KeywordsCheckEvent) {
            TODO("Not yet implemented")
        }


        override fun toggleWordSelection(word: String) {
            // No-op for preview
        }
    }

    val question = "Climate change has led to extreme weather, causing hurricanes, droughts, and floods, prompting efforts to cut emissions and promote sustainability."

    val words = question.split(" ").map { KeywordWord(it) }

    AppTheme {
        KeywordsQuestionContent(
            state = KeywordsCheckState(
                currentQuestion = question,
                selectedWords = words,
                correctAnswers = listOf("Climate", "weather", "emissions") // optional for visual testing
            ),
            controller = mockController
        )
    }
}
