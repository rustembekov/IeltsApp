package com.example.support.feature.factopinion.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.feature.factopinion.model.FactOpinionEvent
import com.example.support.feature.factopinion.model.FactOpinionState
import com.example.support.feature.factopinion.presentation.viewModel.FactOpinionController

@Composable
fun FactOpinionQuestionCard(
    state: FactOpinionState,
    controller: FactOpinionController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(32.dp))
            .background(
                color = AppTheme.colors.homeItemBackground
            )
            .padding(24.dp)
            .padding(top = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.currentQuestion,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FactOrOpinionButton(
                    selectedAnswer = state.selectedAnswer,
                    buttonText = stringResource(R.string.fact),
                    controller = controller
                )
                Spacer(modifier = Modifier.width(32.dp))
                FactOrOpinionButton(
                    selectedAnswer = state.selectedAnswer,
                    buttonText = stringResource(R.string.opinion),
                    controller = controller
                )
            }
        }
    }
}

@Composable
private fun FactOrOpinionButton(
    selectedAnswer: String,
    buttonText: String,
    controller: FactOpinionController
) {
    val result = selectedAnswer == buttonText
    Button(
        onClick = { controller.selectedAnswer(buttonText = buttonText) },
        modifier = Modifier
            .width(130.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (result) AppTheme.colors.buttonGameBackground else AppTheme.colors.buttonGameBackground.copy(
                alpha = 0.5f
            ),
            contentColor = AppTheme.colors.primaryVariant
        )
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun QuestionCardPreview() {
    val mockController = object : FactOpinionController {
        override fun onEvent(event: FactOpinionEvent) {
            TODO("Not yet implemented")
        }

        override fun selectedAnswer(buttonText: String) {
            TODO("Not yet implemented")
        }

    }
    AppTheme {
        FactOpinionQuestionCard(
            state = FactOpinionState(),
            controller = mockController
        )
    }
}