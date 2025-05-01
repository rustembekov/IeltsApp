package com.example.support.feature.essaybuilder.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme

@Composable
fun EssayBlank(
    word: String?,
    onWordDropped: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .dropTarget(onDrop = onWordDropped)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, MaterialTheme.colorScheme.outline)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
            text = word ?: "______",
            style = MaterialTheme.typography.displaySmall,
            color = if (word != null) MaterialTheme.colorScheme.onSurfaceVariant else Color.Gray
        )
    }
}

@Preview
@Composable
private fun EssayBlankPreview() {
    AppTheme {
        EssayBlank(
            word = null,
            onWordDropped = {}
        )
    }
}
