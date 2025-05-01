package com.example.support.feature.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.support.core.navigation.model.NavigationEvent
import com.example.support.core.navigation.Navigator
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.util.ResultCore
import com.example.support.core.data.UserRepository
import com.example.support.feature.login.model.LoginState
import com.example.support.feature.login.model.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigator: Navigator,
    private val userRepository: UserRepository
) : ViewModel(), LoginController {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    override fun onLoginSuccess() {
        viewModelScope.launch {
            navigator.navigate(
                NavigationEvent.NavigateAndPopUp(
                    route = NavigationItem.Home.route,
                    popUp = NavigationItem.Login.route
                )
            )
        }
    }

    override fun onRegisterClick() {
        viewModelScope.launch {
            navigator.navigate(NavigationEvent.Navigate(NavigationItem.Register.route))
        }
    }

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }

            is LoginEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }

            is LoginEvent.Login -> {
                viewModelScope.launch {
                    userRepository.login(
                        _state.value.email,
                        _state.value.password
                    ) { success, message ->
                        _state.update {
                            it.copy(
                                loginResultCore = if (success) {
                                    ResultCore.Success(message)
                                } else {
                                    ResultCore.Failure(message)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}