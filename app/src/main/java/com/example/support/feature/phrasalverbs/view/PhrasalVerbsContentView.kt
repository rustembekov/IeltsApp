package com.example.support.feature.phrasalverbs.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
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
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsEventEvent
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsState
import com.example.support.feature.phrasalverbs.presentation.viewModel.PhrasalVerbsController

@Composable
fun PhrasalVerbsContentView(
    modifier: Modifier = Modifier,
    state: PhrasalVerbsState,
    controller: PhrasalVerbsController
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
                HeaderGameText(strResource = R.string.phrasal_verbs)
                CircularTimer(
                    modifier = Modifier,
                    timer = state.timer
                )
                PhrasalVerbsQuestionCard(
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
                        controller.onEvent(event = PhrasalVerbsEventEvent.Answer)
                    }

                    )
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


@Preview
@Composable
private fun PhrasalVerbsContentViewPreview() {
    val mockController = object : PhrasalVerbsController {
        override fun onEvent(event: PhrasalVerbsEventEvent) {
            TODO("Not yet implemented")
        }

        override fun onInputChange(input: String) {
            TODO("Not yet implemented")
        }

    }
    AppTheme {
        PhrasalVerbsContentView(
            state = PhrasalVerbsState(
                currentQuestion = "After a long and stressful day at work, I just want to ________ on the couch and watch my favorite show. "
            ),
            controller = mockController
        )
    }
}