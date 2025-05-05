package com.example.support.feature.essaybuilder.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.feature.essaybuilder.model.EssayBuilderState

@Composable
fun EssayBlank(
    modifier: Modifier = Modifier,
    blank: EssayBuilderState.BlanksUiModel?,
    onClick: () -> Unit
) {
    val bgColor = when {
        blank == null -> AppTheme.colors.synonymDefaultBackground
        blank.isSelected && blank.isCorrect -> AppTheme.colors.synonymCorrectBackground
        blank.isSelected && !blank.isCorrect -> AppTheme.colors.synonymIncorrectBackground
        else -> AppTheme.colors.synonymDefaultBackground
    }

    val textColor = when {
        blank == null -> AppTheme.colors.homeItemPrimary
        blank.isSelected  -> AppTheme.colors.homeItemPrimary
        else -> AppTheme.colors.homeItemPrimary
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .pointerInput(Unit) { detectTapGestures { onClick() } }
            .clip(MaterialTheme.shapes.medium)
            .background(bgColor)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = blank?.word ?: "______",
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

@Preview
@Composable
private fun EssayBlankPreview() {
    AppTheme {
        EssayBlank(
            blank = null,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun EssayBlankFilledPreview() {
    AppTheme {
        EssayBlank(
            blank = EssayBuilderState.BlanksUiModel(
                word = "pollution",
                isSelected = false,
                isCorrect = false
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun EssayBlankCorrectPreview() {
    AppTheme {
        EssayBlank(
            blank = EssayBuilderState.BlanksUiModel(
                word = "pollution",
                isSelected = true,
                isCorrect = true
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun EssayBlankIncorrectPreview() {
    AppTheme {
        EssayBlank(
            blank = EssayBuilderState.BlanksUiModel(
                word = "education",
                isSelected = true,
                isCorrect = false
            ),
            onClick = {}
        )
    }
}
