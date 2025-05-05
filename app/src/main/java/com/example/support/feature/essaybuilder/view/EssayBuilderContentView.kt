package com.example.support.feature.essaybuilder.view

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
import com.example.support.core.ui.components.animations.CongratulationsView
import com.example.support.core.ui.components.button.CheckButton
import com.example.support.core.ui.components.button.PauseButton
import com.example.support.core.ui.components.text.HeaderGameText
import com.example.support.core.ui.views.CircularTimer
import com.example.support.core.ui.views.pauseDialog.view.PauseDialog
import com.example.support.feature.essaybuilder.model.EssayBuilderEvent
import com.example.support.feature.essaybuilder.model.EssayBuilderState
import com.example.support.feature.essaybuilder.presentation.viewModel.EssayBuilderController

@Composable
fun EssayBuilderContentView(
    modifier: Modifier = Modifier,
    state: EssayBuilderState,
    controller: EssayBuilderController
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
                    strResource = R.string.essay_builder
                )
                CircularTimer(
                    modifier = Modifier,
                    timer = state.timer
                )

                EssayBuilderQuestionContent(
                    state = state,
                    controller = controller
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

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
                    CheckButton(onClick = {
                        controller.onEvent(event = EssayBuilderEvent.AnswerQuestion)
                    }
                    )
                }
            }
        }
        if (state.isShownCorrectAnswer) {
            CongratulationsView()
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
private fun EssayBuilderContentViewPreview() {
    val mockState = EssayBuilderState(
        questionParts = listOf(
            EssayBuilderState.Part.Text("The capital of France is"),
            EssayBuilderState.Part.Blank(0),
            EssayBuilderState.Part.Text(". It is known for the"),
            EssayBuilderState.Part.Blank(1),
            EssayBuilderState.Part.Text(".")
        ),
        currentBlanks = listOf(null, null),
        options = listOf(
            EssayBuilderState.OptionUiModel(
                word = "test1"
            ),
            EssayBuilderState.OptionUiModel(
                word = "test2",
                isSelected = true
            ),
            EssayBuilderState.OptionUiModel(
                word = "test3",
                isSelected = true,
                isUsed = true
            )
        )
    )

    val mockController = object : EssayBuilderController {
        override fun onEvent(event: EssayBuilderEvent) {}
        override fun onWordClick(word: String) {
            TODO("Not yet implemented")
        }

        override fun onBlankClick(index: Int) {
            TODO("Not yet implemented")
        }
    }

    AppTheme {
        EssayBuilderContentView(
            modifier = Modifier.padding(16.dp),
            state = mockState,
            controller = mockController
        )

    }
}