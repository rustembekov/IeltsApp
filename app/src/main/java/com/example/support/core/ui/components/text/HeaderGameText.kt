package com.example.support.core.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.support.R
import com.example.support.core.ui.AppTheme

@Composable
fun HeaderGameText(
    strResource: Int
) {
    Text(
        text = stringResource(strResource),
        color = AppTheme.colors.homeItemPrimary,
        style = MaterialTheme.typography.titleLarge
    )
}

@Preview(showBackground = false)
@Composable
private fun HeaderGameTextPreview() {
    AppTheme {
        HeaderGameText(
            strResource = R.string.fact_opinion
        )
    }
}