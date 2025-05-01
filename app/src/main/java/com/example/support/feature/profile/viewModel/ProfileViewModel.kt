package com.example.support.feature.profile.viewModel

import android.net.Uri
import com.example.support.core.BaseViewModel
import com.example.support.core.util.AvatarManager
import com.example.support.feature.profile.model.ProfileEvent
import com.example.support.feature.profile.model.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val avatarManager: AvatarManager
)  : BaseViewModel<ProfileState, ProfileEvent>(ProfileState()), ProfileController {
    override fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.EditAvatar -> {
                updateState(uiState.value.copy(launchImagePicker = true))
            }
            is ProfileEvent.SettingsProfile -> {
                // Load profile data from repository
                updateState(
                    ProfileState(
                    )
                )
            }
            is ProfileEvent.LanguageProfile -> {
                // Handle sign out logic
            }
            else -> {}
        }
    }

    override fun onImagePicked(uri: Uri) {
        avatarManager.setAvatarUri(uri)
        updateState(uiState.value.copy(
            selectedImageUri = uri,
            launchImagePicker = false
        ))
    }

    override fun onNavigate() {
        TODO("Not yet implemented")
    }
}