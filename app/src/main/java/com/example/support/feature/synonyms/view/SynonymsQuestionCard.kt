package com.example.support.feature.synonyms.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.feature.synonyms.model.SynonymOption
import com.example.support.feature.synonyms.model.SynonymsEvent
import com.example.support.feature.synonyms.model.SynonymsState
import com.example.support.feature.synonyms.presentation.viewModel.SynonymsController

@Composable
fun SynonymsQuestionCard(
    modifier: Modifier = Modifier,
    state: SynonymsState,
    controller: SynonymsController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .padding(16.dp)

    ) {
        itemsIndexed(state.options) { index, option ->
            val backgroundColor = when {
                option.isCorrect == true -> AppTheme.colors.synonymCorrectBackground
                option.isCorrect == false -> AppTheme.colors.synonymIncorrectBackground
                option.isSelected -> AppTheme.colors.synonymSelectedBackground
                else -> AppTheme.colors.synonymDefaultBackground
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(backgroundColor)
                    .clickable(enabled = option.isCorrect == null && state.selectedCount < 3) {
                        controller.toggleSelection(index)
                    }
                    .padding(vertical = 16.dp)
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = option.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SynonymsQuestionCardPreview() {
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
        SynonymsQuestionCard(
            state = SynonymsState(
                category = "Cheerful",
                options = options,
                correctAnswers = listOf("Happy", "Joyful", "Glad")
            ),
            controller = mockController
        )
    }
}
