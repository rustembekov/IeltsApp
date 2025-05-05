package com.example.support.feature.phrasalverbs.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsEventEvent
import com.example.support.feature.phrasalverbs.model.PhrasalVerbsState
import com.example.support.feature.phrasalverbs.presentation.viewModel.PhrasalVerbsController


@Composable
fun PhrasalVerbsQuestionCard(
    modifier: Modifier = Modifier,
    state: PhrasalVerbsState,
    controller: PhrasalVerbsController
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF5C6194)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Common padding for all content
        val contentPadding = 24.dp

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val questionParts = state.currentQuestion.split("________")
            val before = questionParts.getOrNull(0).orEmpty()
            val after = questionParts.getOrNull(1).orEmpty()

            // Text before the blank
            Text(
                text = before.trim(),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            // The input field
            ExpandingUnderlineTextField(
                value = state.userInput,
                onValueChange = { controller.onInputChange(it) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
            )

            // Text after the blank
            Text(
                text = after.trim(),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@Composable
private fun ExpandingUnderlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val measuredText = textMeasurer.measure(
        text = AnnotatedString(if (value.isEmpty()) " " else value),
        style = textStyle
    )
    val minWidth = 60.dp
    val textWidth = with(LocalDensity.current) {
        measuredText.size.width.toDp()
    }
    val animatedWidth by animateDpAsState(
        targetValue = max(minWidth, textWidth + 16.dp),
        label = "textFieldWidth"
    )

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = textStyle,
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .width(animatedWidth)
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
        )
    }
}

@Preview
@Composable
private fun PhrasalVerbsQuestionCardPreview() {
    val mockController = object: PhrasalVerbsController {
        override fun onEvent(event: PhrasalVerbsEventEvent) {}
        override fun onInputChange(input: String) {}
    }

    MaterialTheme {
        PhrasalVerbsQuestionCard(
            state = PhrasalVerbsState(
                currentQuestion = "She ________ a story about being late because she missed the bus, but it wasn't true.",
                userInput = "made up"
            ),
            controller = mockController
        )
    }
}

