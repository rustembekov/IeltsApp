package com.example.support.feature.phrasalverbs.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.support.core.ui.AppTheme
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsEventEvent
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsState
import com.example.support.feature.phrasalverbs.presentation.viewModel.PhrasalVerbsController


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhrasalVerbsQuestionCard(
    modifier: Modifier = Modifier,
    state: PhrasalVerbsState,
    controller: PhrasalVerbsController
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF5C6194)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            val questionParts = state.currentQuestion.split("________")

            // Use FlowRow for proper text wrapping behavior
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.Center,
                maxItemsInEachRow = Int.MAX_VALUE
            ) {
                if (questionParts.isNotEmpty()) {
                    Text(
                        text = questionParts[0],
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                ExpandingUnderlineTextField(
                    value = state.userInput,
                    onValueChange = { controller.onInputChange(it) },
                )

                Spacer(modifier = Modifier.width(4.dp))

                if (questionParts.size > 1) {
                    Text(
                        text = questionParts[1],
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Composable
private fun ExpandingUnderlineTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    val textMeasurer = rememberTextMeasurer()
    val measuredText = textMeasurer.measure(
        text = AnnotatedString(value),
        style = MaterialTheme.typography.bodySmall
    )
    val minWidth = 40.dp
    val textWidth = with(LocalDensity.current) {
        measuredText.size.width.toDp()
    }
    val animatedWidth by animateDpAsState(
        targetValue = max(minWidth, textWidth + 12.dp),
        label = "textFieldWidth"
    )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodySmall.copy(color = Color.White),
        cursorBrush = SolidColor(Color.White),
        modifier = Modifier
            .width(animatedWidth)
            .padding(vertical = 4.dp)
    ) { innerTextField ->
        Box(
            Modifier
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.White,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
                .padding(bottom = 2.dp)
        ) {
            innerTextField()
        }
    }
}



@Preview
@Composable
private fun PhrasalVerbsQuestionCardPreview() {
    val mockController = object: PhrasalVerbsController {
        override fun onEvent(event: PhrasalVerbsEventEvent) {
            TODO("Not yet implemented")
        }

        override fun onInputChange(input: String) {
            TODO("Not yet implemented")
        }

    }
    AppTheme {
        PhrasalVerbsQuestionCard(
            state = PhrasalVerbsState(
                currentQuestion = "After a long and stressful day at work, I just want to ________ on the couch and watch my favorite show. "
            ),
            controller = mockController
        )
    }
}