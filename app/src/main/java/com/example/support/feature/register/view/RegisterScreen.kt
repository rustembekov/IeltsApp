package com.example.support.feature.register.view

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors as ColorApp
import com.example.support.core.ui.views.AuthMenuBar
import com.example.support.core.ui.components.button.AuthButton
import com.example.support.core.ui.components.textField.AuthTextField
import com.example.support.core.util.ResultCore
import com.example.support.feature.register.model.RegisterScreenEvent
import com.example.support.feature.register.model.RegisterState
import com.example.support.feature.register.viewModel.RegisterController

@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    controller: RegisterController,
    state: RegisterState
) {
    val context = LocalContext.current

    LaunchedEffect(state.registerResultCore) {
        state.registerResultCore?.let { registerResult ->
            when (registerResult) {
                is ResultCore.Success -> {
                    controller.onRegisterSuccess()
                }

                is ResultCore.Failure -> {
                    Toast.makeText(
                        context,
                        registerResult.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    RegisterScreenContent(
        state = state,
        controller = controller
    )
}

@Composable
fun RegisterScreenContent(
    state: RegisterState,
    controller: RegisterController
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val backgroundGradient = Brush.linearGradient(
        listOf(
            ColorApp.backgroundGradientFirst, ColorApp.backgroundGradientSecond
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { keyboardController?.hide() }
            }
            .background(backgroundGradient)
    ) {
        AuthMenuBar(
            modifier = Modifier.offset(y = (-30).dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            RegisterForm(
                state = state,
                controller = controller
            )

        }

    }
}


@Composable
private fun RegisterForm(
    state: RegisterState,
    controller: RegisterController
) {
    Box(
        modifier = Modifier
            .height(450.dp)
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
            RegisterHeader()

            AuthTextField(
                value = state.username,
                onValueChange = { controller.onEvent(RegisterScreenEvent.UsernameUpdated(it)) },
                placeholder = stringResource(id = R.string.username),
                leadingIcon = Icons.TwoTone.AccountCircle,
                modifier = Modifier.padding(top = 30.dp)
            )

            AuthTextField(
                value = state.email,
                onValueChange = { controller.onEvent(RegisterScreenEvent.EmailUpdated(it)) },
                placeholder = stringResource(id = R.string.email),
                leadingIcon = Icons.TwoTone.Email,
                modifier = Modifier.padding(top = 30.dp)
            )

            AuthTextField(
                value = state.password,
                onValueChange = { controller.onEvent(RegisterScreenEvent.PasswordUpdated(it)) },
                placeholder = stringResource(id = R.string.password),
                leadingIcon = Icons.TwoTone.Lock,
                isPassword = true,
                modifier = Modifier.padding(top = 30.dp)
            )
            Spacer(modifier = Modifier.padding(top = 30.dp))
            AuthButton(
                onClick = { controller.onEvent(RegisterScreenEvent.RegisterButtonClicked) },
                text = stringResource(id = R.string.register)
            )

            RegisterFooter(
                controller = controller
            )
        }
    }
}

@Composable
private fun RegisterHeader() {
    Text(
        text = stringResource(id = R.string.register),
        style = MaterialTheme.typography.titleLarge,
        color = ColorApp.authContentPrimary
    )
}


@Composable()
private fun RegisterFooter(
    controller: RegisterController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            tint = ColorApp.authContentPrimary,
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = stringResource(id = R.string.back_to_login),
            style = MaterialTheme.typography.bodyMedium,
            color = ColorApp.authContentPrimary,
            modifier = Modifier
                .clickable { controller.onLoginClick() }
        )
    }

}

private class PreviewRegisterController : RegisterController {
    override fun onEvent(event: RegisterScreenEvent) {}
    override fun onLoginClick() {}
    override fun onRegisterSuccess() {}
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val previewState = RegisterState(
        username = "test_user",
        email = "test@example.com",
        password = "password123"
    )
    val previewController = remember { PreviewRegisterController() }
    AppTheme(darkTheme = false) {
        RegisterScreenContent(
            state = previewState,
            controller = previewController
        )
    }
}