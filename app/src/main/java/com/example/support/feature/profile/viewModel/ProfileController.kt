package com.example.support.feature.profile.viewModel

import android.net.Uri
import com.example.support.feature.profile.model.ProfileEvent

interface ProfileController  {
    fun onNavigate()
    fun onEvent(event: ProfileEvent)
    fun onImagePicked(uri: Uri)
    fun loadUser()
}