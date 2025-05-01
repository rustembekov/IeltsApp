package com.example.support.feature.login.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.support.R
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors as ColorApp
import com.example.support.core.ui.views.AuthMenuBar
import com.example.support.core.ui.components.button.AuthButton
import com.example.support.core.ui.components.textField.AuthTextField
import com.example.support.core.util.ResultCore
import com.example.support.feature.login.model.LoginEvent
import com.example.support.feature.login.model.LoginState
import com.example.support.feature.login.viewModel.LoginController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    controller: LoginController,
    state: LoginState
) {
    val context = LocalContext.current

    LaunchedEffect(state.loginResultCore) {
        state.loginResultCore?.let { loginResult ->
            when (loginResult) {
                is ResultCore.Success -> {
                    controller.onLoginSuccess()
                }

                is ResultCore.Failure -> {
                    Toast.makeText(
                        context,
                        loginResult.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    LoginContent(
        state = state,
        controller = controller,
        navHostController = navHostController
    )
}

@Composable
private fun LoginContent(
    state: LoginState,
    controller: LoginController,
    navHostController: NavHostController
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val systemUiController = rememberSystemUiController()
    val backgroundColor = ColorApp.backgroundGradientStatusBar


    SideEffect {
        systemUiController.setStatusBarColor(
            color = backgroundColor,
            darkIcons = false
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures { keyboardController?.hide() } }
            .background(
                Brush.linearGradient(
                    listOf(
                        ColorApp.backgroundGradientFirst, ColorApp.backgroundGradientSecond
                    )
                )
            )

    ) {
        AuthMenuBar()

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginForm(state, controller)
                LoginFooter(
                    onNavigateTo = {
                        navHostController.navigate(NavigationItem.Register.route)
                    }
                )
            }
        }
    }
}

@Composable
private fun LoginForm(
    state: LoginState,
    controller: LoginController
) {
    Box(
        modifier = Modifier
            .height(400.dp)
            .width(340.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(
                color = ColorApp.authContentBackground
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader()
            AuthTextField(
                value = state.email,
                onValueChange = { controller.onEvent(LoginEvent.EmailChanged(it)) },
                placeholder = stringResource(R.string.email),
                leadingIcon = Icons.TwoTone.Email,
                modifier = Modifier.padding(top = 26.dp)
            )
            AuthTextField(
                value = state.password,
                onValueChange = { controller.onEvent(LoginEvent.PasswordChanged(it)) },
                placeholder = stringResource(R.string.password),
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true,
                modifier = Modifier.padding(top = 26.dp)
            )
            AuthButton(
                onClick = { controller.onEvent(LoginEvent.Login) },
                text = stringResource(R.string.login),
                modifier = Modifier.padding(top = 60.dp)
            )

        }
    }
}

@Composable
private fun LoginHeader() {
    Text(
        text = stringResource(R.string.login),
        style = MaterialTheme.typography.titleLarge,
        color = ColorApp.authContentPrimary
    )
}

@Composable
private fun LoginFooter(onNavigateTo: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { onNavigateTo() },
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.no_account_login),
            color = ColorApp.authButtonPrimary,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = stringResource(R.string.sign_up),
            style = MaterialTheme.typography.titleSmall,
            color = ColorApp.authContentPrimary,
            modifier = Modifier

        )
    }
}


@Preview
@Composable
private fun LoginScreenPreview() {
    val mockLoginController = object : LoginController {
        override fun onLoginSuccess() {
            println("Login successful (mock)")
        }

        override fun onRegisterClick() {
            println("Register clicked (mock)")
        }

        override fun onEvent(event: LoginEvent) {
            println("Event received: $event")
        }
    }
    AppTheme(
        darkTheme = false
    ) {
        LoginScreen(
            controller = mockLoginController,
            state = LoginState(),
            navHostController = rememberNavController()
        )
    }
}