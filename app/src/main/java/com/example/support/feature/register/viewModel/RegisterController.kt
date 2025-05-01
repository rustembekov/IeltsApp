package com.example.support.feature.register.viewModel

import com.example.support.feature.register.model.RegisterScreenEvent

interface RegisterController {
    fun onEvent(event: RegisterScreenEvent)
    fun onLoginClick()
    fun onRegisterSuccess()
}