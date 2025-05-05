package com.example.support.core.data

import com.example.support.core.domain.User
import com.example.support.core.util.ResultCore


interface UserManager {
    fun getCurrentUserId(): String?
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit)
    fun logout()
    fun register(
        email: String,
        password: String,
        username: String,
        callback: (Boolean, String) -> Unit
    )
    fun getCurrentUser(callback: (ResultCore<User>) -> Unit)
}