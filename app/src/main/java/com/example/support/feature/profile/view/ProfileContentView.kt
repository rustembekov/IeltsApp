package com.example.support.feature.profile.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors
import com.example.support.core.ui.components.StatsCard
import com.example.support.core.domain.User
import com.example.support.feature.profile.model.ProfileEvent
import com.example.support.feature.profile.model.ProfileState
import com.example.support.feature.profile.viewModel.ProfileController

@Composable
fun ProfileContentView(
    modifier: Modifier = Modifier,
    controller: ProfileController,
    state: ProfileState
) {
    ProfileImagePicker(state = state, onImagePicked = controller::onImagePicked)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colors.backgroundGradientFirst,
                        colors.backgroundGradientSecond
                    )
                )
            ),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileHeader(
                state = state,
                controller = controller
            )
            StatsCard(
                ranking = state.user?.rank.toString(),
                points = state.user?.score.toString()
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                state.settingsList.forEach { profileSettings ->
                    ProfileCardSettings(
                        icon = profileSettings.icon,
                        settingsName = stringResource(profileSettings.name),
                        onClick = {
                            controller.onEvent(event = profileSettings.profileEvent)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileContentViewPreview() {
    val mockController = object : ProfileController {
        override fun onNavigate() {
            TODO("Not yet implemented")
        }

        override fun onEvent(event: ProfileEvent) {
            TODO("Not yet implemented")
        }

        override fun onImagePicked(uri: Uri) {
            TODO("Not yet implemented")
        }


    }
    AppTheme {
        ProfileContentView(
            controller = mockController,
            state = ProfileState(
                user = User(
                    id = "1",
                    email = "johnDoe@gmail.com",
                    username = "johnDoe",
                    password = "johnDoe123",
                    score = 123,
                    rank = 23
                )
            )
        )
    }
}