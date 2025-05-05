package com.example.support.feature.register.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.support.core.data.UserManager
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.util.ResultCore
import com.example.support.feature.register.model.RegisterScreenEvent
import com.example.support.feature.register.model.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val navigator: Navigator,
    private val userRepository: UserManager
) : ViewModel(), RegisterController {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    override fun onRegisterSuccess() {
        viewModelScope.launch {
            navigator.navigate(
                NavigationEvent.NavigateAndPopUp(
                    route = NavigationItem.Home.route,
                    popUp = NavigationItem.Register.route
                )
            )
        }
    }

    override fun onLoginClick() {
        viewModelScope.launch {
            navigator.navigate(NavigationEvent.Navigate(NavigationItem.Login.route))
        }
    }

    override fun onEvent(event: RegisterScreenEvent) {
        when (event) {
            is RegisterScreenEvent.UsernameUpdated -> {
                _state.update { it.copy(username = event.newUsername) }
            }

            is RegisterScreenEvent.EmailUpdated -> {
                _state.update { it.copy(email = event.newEmail) }
            }

            is RegisterScreenEvent.PasswordUpdated -> {
                _state.update { it.copy(password = event.newPassword) }
            }

            is RegisterScreenEvent.RegisterButtonClicked -> register()
            else -> {}
        }
    }

    private fun register() = viewModelScope.launch {
        val username = _state.value.username
        val email = _state.value.email
        val password = _state.value.password
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) return@launch

        userRepository.register(email, password, username) { success, message ->
            _state.update {
                it.copy(
                    registerResultCore = if (success) {
                        ResultCore.Success(message)
                    } else {
                        ResultCore.Failure(message)
                    }
                )
            }
        }
    }
}