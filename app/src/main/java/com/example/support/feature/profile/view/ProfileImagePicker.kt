package com.example.support.feature.profile.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.support.feature.profile.model.ProfileState

@Composable
fun ProfileImagePicker(
    state: ProfileState,
    onImagePicked: (Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> uri?.let { onImagePicked(it) } }
    )

    LaunchedEffect(state.launchImagePicker) {
        if (state.launchImagePicker) {
            launcher.launch("image/*")
        }
    }
}
