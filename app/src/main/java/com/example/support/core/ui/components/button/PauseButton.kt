package com.example.support.core.ui.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme

@Composable
fun PauseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(130.dp)
        ,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.homeItemBackground)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = modifier
                    .size(24.dp),
                tint = AppTheme.colors.buttonGameBackground,
                painter = painterResource(R.drawable.ic_pause),
                contentDescription = stringResource(R.string.pause)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.pause),
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.colors.buttonGameBackground)
        }

    }
}

@Preview(showBackground = false)
@Composable
private fun PauseButtonPreview() {
    AppTheme {
        PauseButton {  }
    }
}