package com.example.support.feature.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors as ColorApp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun HomeCardGameView(
    nameGame: String,
    gameImgResource: Int? = null,
    gameDescription: String? = "",
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(240.dp)
            .width(240.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = ColorApp.backgroundGradientFirst),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            ColorApp.backgroundGradientFirst,
                            ColorApp.backgroundGradientSecond.copy(alpha = 0.8f),
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = nameGame,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )


                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (gameImgResource != null) {
                        Image(
                            painter = painterResource(id = gameImgResource),
                            contentDescription = "Game Image",
                            modifier = Modifier.size(140.dp),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        gameDescription?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun CardGameViewPreview() {
    AppTheme(darkTheme = false) {
        HomeCardGameView(
            onClick = {},
            nameGame = "Choose Keywords",
            gameImgResource = R.drawable.img_keywords_check
        )
    }
}