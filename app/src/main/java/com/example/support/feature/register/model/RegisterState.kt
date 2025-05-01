package com.example.support.feature.register.model

import com.example.support.core.util.ResultCore

sealed class RegisterScreenEvent {
    data class UsernameUpdated(val newUsername: String) : RegisterScreenEvent()
    data class EmailUpdated(val newEmail: String) : RegisterScreenEvent()
    data class PasswordUpdated(val newPassword: String) : RegisterScreenEvent()
    data object RegisterButtonClicked : RegisterScreenEvent()
}

data class RegisterState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val registerResultCore: ResultCore<String>? = null
)