package com.example.support.feature.factopinion.view

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
import com.example.support.feature.factopinion.model.FactOpinionEvent
import com.example.support.feature.factopinion.model.FactOpinionState
import com.example.support.feature.factopinion.presentation.viewModel.FactOpinionController

@Composable
fun FactOpinionContentView(
    modifier: Modifier = Modifier,
    controller: FactOpinionController,
    state: FactOpinionState
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
                    strResource = R.string.fact_opinion
                )
                CircularTimer(
                    modifier = Modifier,
                    timer = state.timer
                )
                FactOpinionQuestionContent(
                    controller = controller,
                    state = state
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
                        controller.onEvent(event = FactOpinionEvent.AnswerQuestion)
                    })
                }
            }
        }

        if (state.isShownCorrectAnswer) {
            CongratulationsView()
        }
        if (state.isPauseDialogVisible) {
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

@Composable
private fun FactOpinionQuestionContent(
    modifier: Modifier = Modifier,
    controller: FactOpinionController,
    state: FactOpinionState
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {

        FactOpinionQuestionCard(
            controller = controller,
            state = state
        )
    }
}

@Preview
@Composable
private fun FactOpinionContentViewPreview() {
    val mockController = object : FactOpinionController {
        override fun onEvent(event: FactOpinionEvent) {
            TODO("Not yet implemented")
        }

        override fun selectedAnswer(buttonText: String) {
            TODO("Not yet implemented")
        }

    }

    AppTheme {
        FactOpinionContentView(
            controller = mockController,
            state = FactOpinionState(
                currentQuestion = "Question Test"
            )
        )
    }
}