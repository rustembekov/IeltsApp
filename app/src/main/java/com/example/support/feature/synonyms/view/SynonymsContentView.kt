package com.example.support.feature.synonyms.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.support.feature.synonyms.model.SynonymOption
import com.example.support.feature.synonyms.model.SynonymsEvent
import com.example.support.feature.synonyms.model.SynonymsState
import com.example.support.feature.synonyms.presentation.viewModel.SynonymsController

@Composable
fun SynonymsContentView(
    modifier: Modifier = Modifier,
    state: SynonymsState,
    controller: SynonymsController
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
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderGameText(
                    strResource = R.string.synonyms
                )
                CircularTimer(
                    modifier = Modifier,
                    timer = state.timer
                )

                SynonymsContentTopic(state = state)
                SynonymsQuestionCard(
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
                        controller.onEvent(SynonymsEvent.AnswerQuestion)
                    })
                }
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
private fun SynonymsContentViewPreview() {
    val mockController = object : SynonymsController {
        override fun onEvent(event: SynonymsEvent) {}
        override fun toggleSelection(index: Int) {}
    }

    val options = listOf(
        SynonymOption("Happy"),
        SynonymOption("Joyful"),
        SynonymOption("Angry"),
        SynonymOption("Glad"),
        SynonymOption("Sad"),
        SynonymOption("Content")
    )

    AppTheme {
        SynonymsContentView(
            state = SynonymsState(
                category = "Emoji",
                mainWord = "Cheerful",
                options = options,
                correctAnswers = listOf("Happy", "Joyful", "Glad")
            ),
            controller = mockController
        )
    }
}
