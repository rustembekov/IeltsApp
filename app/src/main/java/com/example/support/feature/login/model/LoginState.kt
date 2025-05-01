package com.example.support.feature.login.model

import com.example.support.core.util.ResultCore

sealed class LoginUiEvent {
    data class Navigate(val route: String) : LoginUiEvent()
    data class ShowError(val message: String) : LoginUiEvent()
}


data class LoginState(
    val email: String = "",
    val password: String = "",
    val loginResultCore: ResultCore<String>? = null
)
