package com.example.support.feature.synonyms.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors
import com.example.support.feature.synonyms.model.SynonymsState

@Composable
fun SynonymsContentTopic(
    modifier: Modifier = Modifier,
    state: SynonymsState
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(
                color = colors.homeItemBackground
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = state.category,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = " : ",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = state.mainWord,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

}

@Preview
@Composable
private fun SynonymsContentTopicPreview() {
    AppTheme {
        SynonymsContentTopic(
            state = SynonymsState(
                category = "Category",
                mainWord = "Main Word"
            )
        )
    }
}