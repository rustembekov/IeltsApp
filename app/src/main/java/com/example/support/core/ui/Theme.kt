package com.example.support.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.DisposableEffect
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val LocalAppColors = compositionLocalOf { LightAppColors }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Select appropriate colors based on theme
    val appColors = if (darkTheme) DarkAppColors else LightAppColors

    // Create Material ColorScheme
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = appColors.primary,
            secondary = appColors.secondary,
            background = appColors.background,
            surface = appColors.surface,
            error = appColors.error,
            onPrimary = appColors.onPrimary,
            onSecondary = appColors.onSecondary,
            onBackground = appColors.onBackground,
            onSurface = appColors.onSurface,
            onError = appColors.onError
        )
    } else {
        lightColorScheme(
            primary = appColors.primary,
            secondary = appColors.secondary,
            background = appColors.background,
            surface = appColors.surface,
            error = appColors.error,
            onPrimary = appColors.onPrimary,
            onSecondary = appColors.onSecondary,
            onBackground = appColors.onBackground,
            onSurface = appColors.onSurface,
            onError = appColors.onError
        )
    }

    // Handle system UI
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !darkTheme

    DisposableEffect(systemUiController, useDarkIcons) {
        // Set status bar color and icons
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )

        // Hide the system navigation bar
        systemUiController.setNavigationBarColor(
            color = appColors.background,
            darkIcons = useDarkIcons
        )

        // Set system bars visibility
        systemUiController.setSystemBarsColor(
            color = Color.Transparent
        )

        // Hide navigation bar
//        systemUiController.isNavigationBarVisible = false

        onDispose {}
    }

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
}