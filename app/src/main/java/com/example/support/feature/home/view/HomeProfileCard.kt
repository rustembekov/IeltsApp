package com.example.support.feature.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.support.R
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.ui.AppTheme
import com.example.support.core.domain.User
import com.example.support.feature.home.model.HomeEvent
import com.example.support.feature.home.model.HomeState
import com.example.support.feature.home.viewModel.HomeController

@Composable
fun HomeProfileCard(
    state: HomeState,
    controller: HomeController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        AppTheme.colors.backgroundGradientFirst,
                        AppTheme.colors.backgroundGradientSecond.copy(alpha = 0.5f)
                    )
                ),
                shape = RoundedCornerShape(50.dp)

            )
            .clip(
                RoundedCornerShape(50.dp)
            )
    ) {
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
                .offset(x = 40.dp, y = 20.dp)
                .background(
                    color = Color.White.copy(alpha = 0.07f),
                    shape = CircleShape
                )
                .align(Alignment.TopEnd)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
                    .background(AppTheme.colors.homeItemPrimary)
            ) {

                val avatarUri = state.selectedImageUri
                if (avatarUri != null) {
                    AsyncImage(
                        model = avatarUri,
                        contentDescription = null,
                        modifier = Modifier.size(75.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.ic_profile_circle),
                        contentDescription = stringResource(R.string.profile),
                        modifier = Modifier
                            .size(75.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "${state.user?.username}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { controller.onNavigateToRoute(NavigationItem.Profile.route) },
                modifier = Modifier
                    .width(154.dp)
                    .height(38.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.homeItemBackground)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        modifier = Modifier.size(20.4.dp),
                        painter = painterResource(R.drawable.ic_user_edit),
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(R.string.edit_profile),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

            }
        }
    }
}


@Composable
@Preview(showBackground = false)
private fun HomeProfileCardPreview() {
    val mockController = object : HomeController {
        override fun onNavigateToRoute(route: String) {
            TODO("Not yet implemented")
        }

        override fun onEvent(event: HomeEvent) {
            TODO("Not yet implemented")
        }


    }
    AppTheme {
        HomeProfileCard(
            state = HomeState(
                user = User(
                    email = "testing@gmail.com",
                    username = "testing"
                )
            ),
            controller = mockController
        )
    }
}