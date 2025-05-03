package com.example.support.feature.essaybuilder.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.feature.essaybuilder.model.EssayBuilderState

@Composable
fun DraggableWord(
    option: EssayBuilderState.OptionUiModel,
    onClick: () -> Unit
) {
    val alpha = when {
        option.isUsed -> 0.3f
        option.isSelected -> 1f
        else -> 0.7f
    }

    val backgroundColor = if (option.isUsed) {
        AppTheme.colors.synonymSelectedBackground.copy(0.4f)
    } else {
        AppTheme.colors.synonymSelectedBackground
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .graphicsLayer(alpha = alpha)
            .pointerInput(option.word) {
                detectTapGestures(onTap = { onClick() })
            }
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = option.word,
            color = if (option.isUsed) AppTheme.colors.homeItemPrimary.copy(alpha = 0.4f) else AppTheme.colors.homeItemPrimary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Preview
@Composable
private fun DraggableWordPreview() {
    AppTheme {
        DraggableWord(
            option = EssayBuilderState.OptionUiModel(
                word = "Test word",
                isSelected = false,
                isUsed = false
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun DraggableWordUsedPreview() {
    AppTheme {
        DraggableWord(
            option = EssayBuilderState.OptionUiModel(
                word = "Test word",
                isSelected = false,
                isUsed = true
            ),
            onClick = {}
        )
    }
}