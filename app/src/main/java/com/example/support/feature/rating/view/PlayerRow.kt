package com.example.support.feature.rating.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.core.domain.User

@Composable
fun PlayerRow(player: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${player.rank}",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(color = AppTheme.colors.homeItemBackground)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
//                Image(
//                    painter = painterResource()
//                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                PlayerSection(player = player)
            }
        }

    }
}

@Composable
private fun PlayerSection(
    player: User
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = player.username,
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Image(
                modifier = Modifier,
                painter = painterResource(R.drawable.ic_points),
                contentDescription = "Points"
            )
            Text(
                text = stringResource(R.string.score_points, player.score),
                style = MaterialTheme.typography.titleSmall,
                color = AppTheme.colors.homeTextSecondary
            )
        }

    }
}

@Preview(showBackground = false)
@Composable
private fun PlayerRowPreview() {
    AppTheme {
        PlayerRow(player =
            User(
                id = "1",
                username = "John Doe",
                score = 100,
                rank = 1
            )
        )
    }
}