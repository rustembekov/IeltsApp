package com.example.support.feature.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme

@Composable
fun ProfileCardSettings(
    icon: Int,
    settingsName: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = 1.dp,
                color = AppTheme.colors.primaryVariant,
                shape = RoundedCornerShape(24.dp)
            )
            .background(color = AppTheme.colors.homeItemPrimary)
            .padding(24.dp)
            .clickable { onClick()},
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(icon),
            contentDescription = settingsName,
            tint = AppTheme.colors.primaryVariant
        )
        Text(
            text = settingsName,
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.primaryVariant
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun ProfileCardSettingsPreview() {
    AppTheme {
        ProfileCardSettings(
            icon = R.drawable.ic_settings,
            settingsName = "Settings",
            onClick = {}
        )
    }
}