package com.example.support.core.ui.components.button

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    strResource: Int,
    buttonColorRes: Color,
    textColorRes: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColorRes)
    ) {
        Text(
            text = stringResource(strResource),
            style = MaterialTheme.typography.displaySmall,
            color = textColorRes
        )
    }
}

@Preview
@Composable
private fun SubmitButtonPreview() {
    AppTheme {
        SubmitButton(
            onClick = {},
            strResource = R.string.new_game,
            buttonColorRes = MaterialTheme.colorScheme.background,
            textColorRes = AppTheme.colors.primaryVariant
        )
    }
}