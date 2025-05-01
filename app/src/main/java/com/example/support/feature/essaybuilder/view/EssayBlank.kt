package com.example.support.feature.essaybuilder.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.feature.essaybuilder.model.EssayBuilderState

@Composable
fun EssayBlank(blank: EssayBuilderState.BlanksUiModel?, onClick: () -> Unit) {
    val bgColor = when {
        blank == null -> Color.Gray
        blank.isCorrect -> Color(0xFF4CAF50) // green
        !blank.isCorrect && blank.isSelected -> Color(0xFFF44336) // red
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .pointerInput(Unit) { detectTapGestures { onClick() } }
            .clip(MaterialTheme.shapes.medium)
            .background(bgColor)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(text = blank?.word ?: "______")
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
