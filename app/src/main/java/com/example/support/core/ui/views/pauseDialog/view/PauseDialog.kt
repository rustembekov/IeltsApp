package com.example.support.core.ui.views.pauseDialog.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.components.button.PlayButton
import com.example.support.core.ui.components.button.QuitButton

@Composable
fun PauseDialog(
    onQuit: () -> Unit,
    onResume: () -> Unit
) {
    Dialog(onDismissRequest = onResume) {
        AnimatedVisibility(
            visible = true,
            enter = scaleIn(animationSpec = tween(300)),
            exit = scaleOut(animationSpec = tween(300))
        ) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .background(Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.paused),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        PlayButton(
                            onClick = onResume
                        )

                        QuitButton(
                            onClick = onQuit
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PauseDialogPreview() {
    AppTheme {
        PauseDialog(
            onQuit = {},
            onResume = {}
        )
    }
}