package com.example.support.core.ui.components.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()


    val secondaryColor = AppTheme.colors.authTextFieldSecondary
    val backgroundColor = AppTheme.colors.authTextFieldBackground
    val activeColor = AppTheme.colors.authTextFieldPrimary

    val currentColor = when {
        isFocused -> activeColor
        else -> secondaryColor
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        placeholder = {
            Text(
                text = placeholder,
                color = currentColor,
                style = MaterialTheme.typography.titleMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = currentColor,
                modifier = Modifier.size(15.dp)
            )
        },
        textStyle = MaterialTheme.typography.displaySmall,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier
            .height(50.dp)
            .width(300.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(color = backgroundColor),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppTheme.colors.authTextFieldBackground,
            unfocusedContainerColor = AppTheme.colors.authTextFieldBackground,
            focusedTextColor = currentColor,
            unfocusedTextColor = currentColor,
            focusedLeadingIconColor = currentColor,
            unfocusedLeadingIconColor = currentColor,
            focusedPlaceholderColor = currentColor,
            unfocusedPlaceholderColor = currentColor,
            focusedIndicatorColor = activeColor,
            unfocusedIndicatorColor = secondaryColor,
            cursorColor = activeColor
        )
    )
}


@Preview(showBackground = true)
@Composable
private fun AuthTextFieldPreview() {
    AppTheme(darkTheme = false) {
        AuthTextField(
            value = "john.doe@example.com",
            onValueChange = {},
            placeholder = "Email",
            leadingIcon = Icons.Default.MailOutline,
            isPassword = false
        )
    }
}
