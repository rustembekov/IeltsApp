package com.example.support.feature.keywordscheck.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.BaseGameViewModel
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors
import com.example.support.core.ui.components.button.CheckButton
import com.example.support.core.ui.components.button.PauseButton
import com.example.support.core.ui.components.text.HeaderGameText
import com.example.support.core.ui.views.CircularTimer
import com.example.support.core.ui.views.pauseDialog.view.PauseDialog
import com.example.support.feature.keywordscheck.model.KeywordWord
import com.example.support.feature.keywordscheck.model.KeywordsCheckEvent
import com.example.support.feature.keywordscheck.model.KeywordsCheckState
import com.example.support.feature.keywordscheck.presentation.viewModel.KeywordsCheckController

@Composable
fun KeywordsCheckContentView(
    modifier: Modifier = Modifier,
    state: KeywordsCheckState,
    controller: KeywordsCheckController
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colors.backgroundGradientFirst,
                        colors.backgroundGradientSecond
                    )
                )
            ),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HeaderGameText(
                    strResource = R.string.keywords_check
                )
                CircularTimer(
                    modifier = Modifier,
                    timer = state.timer
                )
                KeywordsQuestionContent(
                    controller = controller,
                    state = state
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PauseButton(
                    onClick = {
                        if (controller is BaseGameViewModel<*, *>) {
                            controller.onPauseClicked()
                        }
                    }
                )
                CheckButton(
                    onClick = {
                        controller.onEvent(KeywordsCheckEvent.AnswerQuestion)
                    }
                )
            }
        }
        if (state.isPaused) {
            (controller as? BaseGameViewModel<*, *>)?.let {
                PauseDialog(
                    onQuit = {
                        it.onQuitGame()
                    },
                    onResume = {
                        it.onResumePauseDialog()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun KeywordsCheckContentViewPreview() {
    val mockController = object : KeywordsCheckController {
        override fun onEvent(event: KeywordsCheckEvent) {
            TODO("Not yet implemented")
        }

        override fun toggleWordSelection(word: String) {
            TODO("Not yet implemented")
        }

    }
    val question =
        "Climate change has led to extreme weather, causing hurricanes, droughts, and floods, prompting efforts to cut emissions and promote sustainability."

    val words = question.split(" ").map { KeywordWord(it) }

    AppTheme(
        darkTheme = false
    ) {
        KeywordsCheckContentView(
            state = KeywordsCheckState(
                currentQuestion = question,
                selectedWords = words,
                correctAnswers = listOf(
                    "Climate",
                    "weather",
                    "emissions"
                ) // optional for visual testing
            ),
            controller = mockController
        )
    }
}