package com.example.support.feature.profile.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors as ColorApp
import com.example.support.feature.profile.model.ProfileEvent
import com.example.support.feature.profile.model.ProfileState
import com.example.support.feature.profile.viewModel.ProfileController

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    state: ProfileState,
    controller: ProfileController
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        ColorApp.backgroundGradientFirst,
                        ColorApp.backgroundGradientSecond.copy(alpha = 0.5f)
                    )
                )
            )
            .padding(vertical = 32.dp, horizontal = 24.dp)


    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ){
            Text(
                text = stringResource(R.string.profile),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            FormSection(
                state = state,
                controller = controller
            )

        }

        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(x = (-70).dp, y = (-90).dp)
                .background(
                    color = ColorApp.backgroundGradientFirst.copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .align(Alignment.TopStart)

        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = 150.dp)
                .background(
                    color = ColorApp.backgroundGradientFirst.copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun FormSection(
    state: ProfileState,
    controller: ProfileController
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(
            modifier = Modifier
                .size(75.dp)
                .clip(CircleShape)
                .background(color = ColorApp.homeItemBackground),
            contentAlignment = Alignment.Center

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
                    painter = painterResource(R.drawable.ic_user_edit),
                    contentDescription = stringResource(R.string.profile),
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        Text(
            text = state.user?.username ?: "No Username",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileEditButton(
                onClick =  {controller.onEvent(ProfileEvent.EditAvatar)},
                strResource = R.string.edit_picture
            )
            ProfileEditButton(
                onClick =  {},
                strResource = R.string.edit_name
            )
        }

    }
}

@Composable
private fun ProfileEditButton(
    onClick: () -> Unit = {},
    strResource: Int
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(154.dp)
            .height(38.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.homeItemBackground)
    ) {
        Text(
            text = stringResource(strResource),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary)
    }
}

@Preview(showBackground = false)
@Composable
private fun ProfileHeaderPreview() {
    val mockController = object: ProfileController {
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
        ProfileHeader(
            state = ProfileState(),
            controller = mockController
        )
    }
}