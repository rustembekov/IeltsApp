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
import com.example.support.feature.essaybuilder.viewModel.EssayBuilderController
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.example.support.feature.essaybuilder.model.EssayBuilderEvent


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EssayBuilderQuestionContent(
    modifier: Modifier = Modifier,
    state: EssayBuilderState,
    controller: EssayBuilderController
) {
    val blanks = remember { mutableStateListOf(*state.currentBlanks.toTypedArray()) }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.questionParts.forEach { part ->
                when (part) {
                    is EssayBuilderState.Part.Text -> {
                        BasicText(
                            text = part.text,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    is EssayBuilderState.Part.Blank -> {
                        EssayBlank(
                            word = blanks[part.index],
                            onWordDropped = { word ->
                                blanks[part.index] = word
                                controller.updateBlanks(blanks)
                            }
                        )
                    }
                }
            }
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            state.options.forEach { option ->
                DraggableWord(word = option)
            }
        }

    }
}



@Preview(showBackground = true)
@Composable
private fun EssayBuilderQuestionContentPreview() {
    val mockState = EssayBuilderState(
        questionParts = listOf(
            EssayBuilderState.Part.Text("The capital of France is"),
            EssayBuilderState.Part.Blank(0),
            EssayBuilderState.Part.Text(". It is known for the"),
            EssayBuilderState.Part.Blank(1),
            EssayBuilderState.Part.Text(".")
        ),
        options = listOf("Paris", "Eiffel Tower", "pizza", "Berlin", "museum", "croissant"),
        currentBlanks = listOf(null, null) // Two blanks, initially empty
    )

    val mockController = object : EssayBuilderController {
        override fun onEvent(event: EssayBuilderEvent) {}
        override fun updateBlanks(blanks: List<String?>) {}
    }

    AppTheme {
        EssayBuilderQuestionContent(
            modifier = Modifier.padding(16.dp),
            state = mockState,
            controller = mockController
        )
    }
}
