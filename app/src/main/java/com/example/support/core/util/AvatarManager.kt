package com.example.support.core.util

import android.net.Uri
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AvatarManager @Inject constructor() {

    private var avatarUri: Uri? = null

    fun setAvatarUri(uri: Uri) {
        avatarUri = uri
    }

    fun getAvatarUri(): Uri? = avatarUri

    fun clearAvatar() {
        avatarUri = null
    }
}

