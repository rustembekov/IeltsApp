package com.example.support.feature.essaybuilder.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.feature.essaybuilder.model.EssayBuilderState
import com.example.support.feature.essaybuilder.presentation.viewModel.EssayBuilderController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
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
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                InlineTextWithBlanks(
                    tokens = state.tokens,
                    currentBlanks = state.currentBlanks,
                    onBlankClick = { controller.onBlankClick(it) }
                )
            }
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.options.forEach { option ->
                DraggableWord(
                    option = option,
                    onClick = { if (!option.isUsed) controller.onWordClick(option.word) }
                )
            }
        }
    }
}

@Composable
fun InlineTextWithBlanks(
    tokens: List<EssayBuilderState.Token>,
    currentBlanks: List<EssayBuilderState.BlanksUiModel?>,
    onBlankClick: (Int) -> Unit
) {
    val textStyle = MaterialTheme.typography.bodyMedium
    val textColor = AppTheme.colors.homeItemPrimary
    val density = LocalDensity.current

    Layout(
        content = {
            // Create composables for each token
            tokens.forEachIndexed { index, token ->
                when {
                    token.isBlank -> {
                        val blankModel = currentBlanks.getOrNull(token.blankIndex)
                        EssayBlank(
                            blank = blankModel,
                            onClick = { onBlankClick(token.blankIndex) }
                        )
                    }
                    token.isSpace -> {
                        Spacer(Modifier.width(0.dp))
                    }
                    else -> {
                        Text(
                            text = token.content,
                            style = textStyle,
                            color = textColor
                        )
                    }
                }
            }
        }
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(Constraints()) }

        var currentX = 0
        var currentY = 0
        var lineHeight = 0
        val positions = mutableListOf<Offset?>()
        val spacingPx = with(density) { 4.dp.toPx() }.toInt()

        placeables.forEachIndexed { index, placeable ->
            val token = tokens.getOrNull(index) ?: return@forEachIndexed

            if (token.isSpace) {
                positions.add(null)

                if (currentX > 0) {
                    currentX += spacingPx
                }
                return@forEachIndexed
            }

            if (currentX + placeable.width > constraints.maxWidth && currentX > 0) {
                currentY += lineHeight
                currentX = 0
                lineHeight = 0
            }

            positions.add(Offset(currentX.toFloat(), currentY.toFloat()))

            lineHeight = maxOf(lineHeight, placeable.height)

            currentX += placeable.width

            if (!token.isBlank && index < tokens.size - 1 &&
                !tokens[index + 1].isBlank && currentX + spacingPx < constraints.maxWidth) {
                currentX += spacingPx
            }
        }

        val totalHeight = currentY + lineHeight

        layout(constraints.maxWidth, totalHeight) {
            placeables.forEachIndexed { index, placeable ->
                val position = positions[index] ?: return@forEachIndexed
                placeable.place(position.x.toInt(), position.y.toInt())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EssayBuilderQuestionContentPreview() {
    val mockTokens = listOf(
        EssayBuilderState.Token(content = "In", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "modern", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "society,", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(isBlank = true, blankIndex = 0),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "has", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "become", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "a", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "major", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "concern", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "for", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "governments", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "worldwide.", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "Addressing", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "this", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "issue", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "is", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "essential", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "to", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "ensure", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "a", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(isBlank = true, blankIndex = 1),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "future", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "for", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "the", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "next", isBlank = false),
        EssayBuilderState.Token(isSpace = true),
        EssayBuilderState.Token(content = "generations.", isBlank = false)
    )

    val mockState = EssayBuilderState(
        questionParts = listOf(
            EssayBuilderState.Part.Text("In modern society,"),
            EssayBuilderState.Part.Blank(0),
            EssayBuilderState.Part.Text("has become a major concern for governments worldwide.\u2028Addressing this issue is essential to ensure a"),
            EssayBuilderState.Part.Blank(1),
            EssayBuilderState.Part.Text("future for the next generations.")
        ),
        tokens = mockTokens,
        options = listOf(
            EssayBuilderState.OptionUiModel("pollution"),
            EssayBuilderState.OptionUiModel("education"),
            EssayBuilderState.OptionUiModel("sustainable"),
            EssayBuilderState.OptionUiModel("dangerous")
        ),
        currentBlanks = listOf(null, null)
    )

    val mockController = object : EssayBuilderController {
        override fun onEvent(event: EssayBuilderEvent) {}
        override fun onWordClick(word: String) {}
        override fun onBlankClick(index: Int) {}
    }

    AppTheme {
        EssayBuilderQuestionContent(
            modifier = Modifier.padding(16.dp),
            state = mockState,
            controller = mockController
        )
    }
}