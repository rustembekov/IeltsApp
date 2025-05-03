package com.example.support.core.ui.views

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme

@Composable
fun CircularTimer(
    modifier: Modifier = Modifier,
    timer: Int,
    totalDuration: Int = 20
) {
    val progress = (timer.toFloat() / totalDuration).coerceIn(0f, 1f)

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 500),
        label = "TimerAnimation"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(48.dp)) {
            drawArc(
                color = Color.Gray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(5.dp.toPx())
            )
            drawArc(
                color = Color.Green,
                startAngle = -90f,
                sweepAngle = animatedProgress * 360f,
                useCenter = false,
                style = Stroke(5.dp.toPx())
            )
        }
        Text(
            text = "$timer",
            style = MaterialTheme.typography.bodyLarge,
            color = AppTheme.colors.primary
        )
    }
}




@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun CircularTimePreview() {
    AppTheme(
        darkTheme = false
    ) {
        CircularTimer(timer = 70, totalDuration = 70)
    }
}