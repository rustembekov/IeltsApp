package com.example.support.feature.profile.model

import android.net.Uri
import com.example.support.R
import com.example.support.core.domain.User

data class ProfileState(
    val user: User? = null,
    val result: ProfileResult? = null,
    val settingsList: List<ProfileSettings> = listOf(
        ProfileSettings(icon = R.drawable.ic_settings, name = R.string.settings, ProfileEvent.SettingsProfile),
        ProfileSettings(icon = R.drawable.ic_global, name = R.string.language, ProfileEvent.LanguageProfile),
        ProfileSettings(icon = R.drawable.ic_block, name = R.string.login_security, ProfileEvent.LoginSecurityProfile),
        ProfileSettings(icon = R.drawable.ic_profile_delete, name = R.string.delete_account, ProfileEvent.DeleteAccountProfile)
    ),
    val selectedImageUri: Uri? = null,
    val launchImagePicker: Boolean = false,

    )

data class ProfileSettings(
    val icon: Int,
    val name: Int,
    val profileEvent: ProfileEvent
)

sealed class ProfileEvent {
    data object EditAvatar : ProfileEvent()
    data object SettingsProfile : ProfileEvent()
    data object LanguageProfile : ProfileEvent()
    data object LoginSecurityProfile : ProfileEvent()
    data object DeleteAccountProfile : ProfileEvent()
}

sealed class ProfileResult {
    data object Success : ProfileResult()
    data object Loading : ProfileResult()
    data class Error(val message: String) : ProfileResult()
}