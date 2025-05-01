package com.example.support.core.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors

@Composable
fun CustomBackgroundContainer(
    modifierWhole: Modifier = Modifier,
    modifierContent: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifierWhole
            .fillMaxWidth()
            .fillMaxHeight(0.55f)
            .background(
                Brush.linearGradient(
                    listOf(
                        colors.backgroundGradientFirst,
                        colors.backgroundGradientSecond.copy(alpha = 0.5f)
                    )
                )
            )
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 50.dp,
                    bottomEnd = 50.dp
                )
            )
    ) {

        Column (
            modifier = modifierContent
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
           content()
        }

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
                .size(200.dp)
                .offset(x = 40.dp, y= 20.dp)
                .background(
                    color = Color.White.copy(alpha = 0.07f),
                    shape = CircleShape
                )
                .align(Alignment.TopEnd)
        )


    }
}


@Preview(showBackground = false)
@Composable
private fun BackgroundItemPreview() {
    AppTheme {
        CustomBackgroundContainer{}
    }
}