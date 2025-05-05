package com.example.support.feature.profile.view

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.views.ErrorView
import com.example.support.feature.profile.model.ProfileEvent
import com.example.support.feature.profile.model.ProfileResult
import com.example.support.feature.profile.model.ProfileState
import com.example.support.feature.profile.viewModel.ProfileController


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    controller: ProfileController,
    state: ProfileState
) {
    LaunchedEffect(Unit) {
        controller.loadUser()
    }
    ProfileImagePicker(state = state, onImagePicked = controller::onImagePicked)

    when (state.result) {
        is ProfileResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ProfileResult.Error -> {
            val errorMessage = state.result.message
            ErrorView(
                message = errorMessage,
                onRetry = {
                    controller.loadUser()
                }
            )
        }

        else -> {
            ProfileContentView(
                controller = controller,
                state = state,
                modifier = modifier
            )
        }
    }

}

@Preview
@Composable
private fun ProfileScreenPreview() {
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

        override fun loadUser() {
            TODO("Not yet implemented")
        }


    }
    AppTheme {
        ProfileScreen(
            controller = mockController,
            state = ProfileState()
        )
    }
}