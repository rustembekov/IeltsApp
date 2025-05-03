package com.example.support.feature.rating.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.core.domain.User

@Composable
fun PlayerRow(player: User, avatarUrl: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(20.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "${player.rank}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(8.dp))
        Log.d("Avatar", avatarUrl.toString())
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(color = AppTheme.colors.homeItemBackground),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .components {
                    add(SvgDecoder.Factory())
                }
                .build()

            if (avatarUrl == null) {
                Image(
                    painter = painterResource(R.drawable.ic_profile_circle),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            } else {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "Avatar",
                    imageLoader = imageLoader,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )

            }

            Spacer(modifier = Modifier.width(16.dp))
            PlayerSection(player = player)
        }


    }
}

@Composable
private fun PlayerSection(
    player: User
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = player.username,
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.primary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(R.drawable.ic_points),
                contentDescription = "Points"
            )
            Text(
                text = stringResource(R.string.score_points, player.score),
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.colors.homeTextSecondary
            )
        }

    }
}

@Preview(showBackground = false)
@Composable
private fun PlayerRowPreview() {
    AppTheme {
        PlayerRow(
            player =
            User(
                id = "1",
                username = "John Doe",
                score = 100,
                rank = 1
            ),
            avatarUrl = null
        )
    }
}