package com.example.support.feature.rating.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors as ColorApp
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import com.example.support.R
import com.example.support.core.domain.User

@Composable
fun PodiumItem(
    visible: Boolean = true,
    user: User,
    height: Dp,
    avatarUrl: String?
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight }
        ) + fadeIn(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.width(60.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(ColorApp.podiumRankGameBackground),
                    contentAlignment = Alignment.Center
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
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(height)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    ColorApp.backgroundGradientSecond,
                                    ColorApp.backgroundGradientFirst
                                )
                            )
                        ),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    Text(
                        text = "${user.rank}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.colors.homeTextSecondary,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.colors.homeItemPrimary,
                    maxLines = 1
                )
                Text(
                    text = stringResource(R.string.score_points, user.score),
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.colors.homeTextSecondary
                )
            }
        }
    }
}



@Preview(showBackground = false)
@Composable
private fun PodiumItemPreview() {
    AppTheme(darkTheme = false) {
        PodiumItem(
            user = User(
                rank = 1,
                score = 1200,
                username = "testing@gmail.com"
            ),
            height = 48.dp,
            avatarUrl = null

        )

    }
}
