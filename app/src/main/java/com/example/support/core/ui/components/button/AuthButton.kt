package com.example.support.core.ui.components.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String
) {
    Button(
        modifier = modifier
            .width(300.dp)
            .height(50.dp),
        onClick = onClick,
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.authButtonBackground,
        )
    )
    {
        Text(
            text = text,
            color = AppTheme.colors.authButtonPrimary,
            style = MaterialTheme.typography.titleMedium
        )
    }

}

@Preview(showBackground = false)
@Composable
private fun AuthButtonPreview() {
    AppTheme(darkTheme = false) {
        AuthButton(
            text = "Login"
        )
    }
}