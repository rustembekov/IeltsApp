package com.example.support.feature.profile.viewModel

import android.net.Uri
import com.example.support.core.BaseViewModel
import com.example.support.core.data.UserManager
import com.example.support.core.util.AvatarManager
import com.example.support.core.util.ResultCore
import com.example.support.feature.profile.model.ProfileEvent
import com.example.support.feature.profile.model.ProfileResult
import com.example.support.feature.profile.model.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val avatarManager: AvatarManager,
    private val userRepository: UserManager
)  : BaseViewModel<ProfileState, ProfileEvent>(ProfileState()), ProfileController {
    override fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.EditAvatar -> {
                updateState(uiState.value.copy(launchImagePicker = true))
            }
            is ProfileEvent.SettingsProfile -> {
                updateState(
                    ProfileState(
                    )
                )
            }
            is ProfileEvent.LanguageProfile -> {

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

    override fun loadUser() {
        updateState(uiState.value.copy(result = ProfileResult.Loading))

        userRepository.getCurrentUser { result ->
            when (result) {
                is ResultCore.Success -> {
                    val latestState = uiState.value
                    updateState(
                        latestState.copy(
                            user = result.data,
                            result = ProfileResult.Success
                        )
                    )
                }

                is ResultCore.Failure -> {
                    val latestState = uiState.value
                    updateState(
                        latestState.copy(
                            result = ProfileResult.Error(result.message)
                        )
                    )
                }
            }
        }
    }

    override fun onNavigate() {
        TODO("Not yet implemented")
    }
}