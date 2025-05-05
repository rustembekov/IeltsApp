package com.example.support.feature.gamecompletion.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.feature.gamecompletion.model.GameCompletionState
import kotlinx.coroutines.delay

@Composable
fun GameCompletionCard(
    modifier: Modifier = Modifier,
    state: GameCompletionState
) {
    val animatedScore = remember { mutableIntStateOf(state.previousScore) }
    val animatedRank = remember { mutableIntStateOf(state.previousRank) }

    LaunchedEffect(state.newScore) {
        for (i in state.previousScore..state.newScore) {
            animatedScore.intValue = i
            delay(20) // Adjust speed here
        }
    }

    LaunchedEffect(state.newRank) {
        val range = if (state.newRank > state.previousRank)
            state.previousRank..state.newRank
        else
            state.previousRank downTo state.newRank

        for (i in range) {
            animatedRank.intValue = i
            delay(30)
        }
    }

    Card(
        modifier = modifier
            .height(92.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.homeItemPrimary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RankItemAnimated(
                icon = R.drawable.ic_ranking,
                label = stringResource(R.string.ranking),
                value = animatedRank.intValue,
                valueColor = Color(0xffF5BA46),
                labelColor = AppTheme.colors.homeTextPrimary
            )

            VerticalDivider(
                modifier = Modifier
                    .height(51.5.dp)
                    .width(2.dp),
                color = Color.LightGray
            )

            RankItemAnimated(
                icon = R.drawable.ic_points,
                label = stringResource(R.string.points),
                value = animatedScore.intValue,
                valueColor = Color(0xFF4CAF50),
                labelColor = AppTheme.colors.homeTextPrimary,
                iconTint = Color(0xFF6250B5)
            )
        }
    }
}

@Composable
private fun RankItemAnimated(
    icon: Int,
    label: String,
    value: Int,
    valueColor: Color,
    labelColor: Color,
    iconTint: Color = Color.Unspecified
) {
    Row(
        modifier = Modifier.padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = label,
                color = labelColor,
                style = MaterialTheme.typography.bodyMedium
            )

            val animatedScale by animateFloatAsState(
                targetValue = 1.1f,
                animationSpec = keyframes {
                    durationMillis = 300
                    1.0f at 0
                    1.2f at 100
                    1.1f at 200
                    1.0f at 300
                },
                label = "ScaleAnim"
            )

            Text(
                text = value.toString(),
                color = valueColor,
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.scale(animatedScale)
            )
        }
    }
}



@Preview
@Composable
private fun GameCompletionCardPreview() {
    AppTheme {
        GameCompletionCard(
            state = GameCompletionState()
        )
    }
}