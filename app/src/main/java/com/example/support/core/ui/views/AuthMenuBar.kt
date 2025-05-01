package com.example.support.core.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors

@Composable
fun AuthMenuBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(
                Brush.linearGradient(
                    listOf(
                        colors.backgroundGradientFirst,
                        colors.backgroundGradientSecond.copy(alpha = 0.5f)
                    )
                )
            )
            .clip(RoundedCornerShape(50.dp))
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(x = (-50).dp, y = (-30).dp)
                .background(
                    color = Color.White.copy(alpha = 0.05f),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = 30.dp, y = (-30).dp)
                .background(
                    color = Color.White.copy(alpha = 0.07f),
                    shape = CircleShape
                )
                .align(Alignment.TopEnd)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = modifier
                    .size(300.dp)
                    .align(Alignment.Center),
                painter = painterResource(R.drawable.menu_bar),
                contentDescription = null,
            )
        }
    }
}


@Preview(showBackground = false)
@Composable
private fun AuthMenuBarPreview() {
    AppTheme(darkTheme = false) {
        AuthMenuBar()
    }
}