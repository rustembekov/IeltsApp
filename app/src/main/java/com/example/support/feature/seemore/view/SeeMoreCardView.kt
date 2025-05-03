package com.example.support.feature.seemore.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors

@Composable
fun SeeMoreCardView(
    nameGame: String,
    gameDescription: String? = "",
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .width(180.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = colors.homeItemBackground),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colors.backgroundGradientFirst,
                            colors.backgroundGradientSecond.copy(alpha = 0.8f),
                        )
                    )
                )
                .padding(16.dp)
            ,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = nameGame,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = gameDescription ?: "No Description",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

            }


            PlayButton(onClick = onClick)
        }
    }
}

@Composable
private fun PlayButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            2.5.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .height(40.dp)
            .width(120.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        Text(
            text = stringResource(R.string.play),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview
@Composable
private fun SeeMoreCardViewPreview() {
    AppTheme(
        darkTheme = false
    ) {
        SeeMoreCardView(
            nameGame = "Essay Builder",
            gameDescription = "Choose the correct words to form a coherent and well-structured IELTS essay.",
            onClick = {}
        )
    }
}
