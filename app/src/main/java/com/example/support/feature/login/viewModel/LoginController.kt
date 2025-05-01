package com.example.support.feature.login.viewModel

import com.example.support.feature.login.model.LoginEvent

interface LoginController {
    fun onLoginSuccess()
    fun onRegisterClick()
    fun onEvent(event: LoginEvent)
}