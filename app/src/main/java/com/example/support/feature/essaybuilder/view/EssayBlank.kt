package com.example.support.feature.essaybuilder.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.feature.essaybuilder.model.EssayBuilderState

@Composable
fun EssayBlank(
    modifier: Modifier = Modifier,
    blank: EssayBuilderState.BlanksUiModel?, onClick: () -> Unit
) {
    val bgColor = when {
        blank == null -> AppTheme.colors.synonymDefaultBackground
        blank.isCorrect -> AppTheme.colors.synonymCorrectBackground
        !blank.isCorrect -> AppTheme.colors.synonymIncorrectBackground
        else -> Color.Gray
    }

    Box(
        modifier = modifier
            .pointerInput(Unit) { detectTapGestures { onClick() } }
            .clip(MaterialTheme.shapes.medium)
            .background(bgColor)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = blank?.word ?: "______",
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.homeItemPrimary
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
