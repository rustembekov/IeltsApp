package com.example.support.feature.essaybuilder.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.feature.essaybuilder.model.EssayBuilderState
import com.example.support.feature.essaybuilder.presentation.viewModel.EssayBuilderController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.example.support.feature.essaybuilder.model.EssayBuilderEvent


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EssayBuilderQuestionContent(
    modifier: Modifier = Modifier,
    state: EssayBuilderState,
    controller: EssayBuilderController
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            state.questionParts.forEach { part ->
                when (part) {
                    is EssayBuilderState.Part.Text -> {
                        Text(
                            text = part.text,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    is EssayBuilderState.Part.Blank -> {
                        EssayBlank(
                            blank = state.currentBlanks.getOrNull(part.index),
                            onClick = { controller.onBlankClick(part.index) }
                        )
                    }
                }
            }
        }

        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            state.options.forEach { option ->
                DraggableWord(
                    option = option,
                    onClick = { if (!option.isUsed) controller.onWordClick(option.word) }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun EssayBuilderQuestionContentPreview() {
    val mockState = EssayBuilderState(
        questionParts = listOf(
            EssayBuilderState.Part.Text("In modern society,"),
            EssayBuilderState.Part.Blank(0),
            EssayBuilderState.Part.Text("has become a major concern for governments worldwide.\u2028Addressing this issue is essential to ensure a"),
            EssayBuilderState.Part.Blank(1),
            EssayBuilderState.Part.Text("future for the next generations.")
        ),
//        options = listOf("pollution", "education", "sustainable", "dangerous"),
        currentBlanks = listOf(null, null)
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
        EssayBuilderQuestionContent(
            modifier = Modifier.padding(16.dp),
            state = mockState,
            controller = mockController
        )
    }
}
