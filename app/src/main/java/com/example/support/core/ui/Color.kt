package com.example.support.core.ui

import androidx.compose.ui.graphics.Color

object BaseColors {
    // Add more base colors
    // Grays, reds, greens, etc.
}

// Component-specific colors for light theme
object LightComponentColors {
    val BackgroundGradientStatusBar = Color(0xFF6d76ba)

    val BackgroundGradientFirst = Color(0xFF7F8AD4)
    val BackgroundGradientSecond = Color(0xFF1E244D)

    val AuthTextFieldPrimary = Color.Black
    val AuthTextFieldSecondary = Color(0xFFEAEAEA)
    val AuthTextFieldBackground = Color.White

    val AuthButtonPrimary = Color.White
    val AuthButtonBackground = Color(0xFF898FEC)

    val AuthContentPrimary = Color(0xFF898FEC)
    val AuthContentBackground = Color(0xFFEAEAEA)

    val TabBarSelectedPrimary = Color(0xFF120321)
    val TabBarSelectedSecondary = Color(0xFF7C6594)
    val TabBarSelectedBackground = Color(0xFFE7E8FF)

    val HomeItemBackground = Color(0xFF595D99)
    val HomeItemPrimary = Color(0xFFE7E8FF)
    val HomeTextPrimary = Color(0xFF120321)
    val HomeTextSecondary = Color(0xFF898FEC)

    val CardGameContainerBackground = Color(0xFF898FEC)
    val PodiumRankGameBackground = Color(0xFFE0DEE1)
    val ButtonGameBackground = Color(0xFFE6D8F8)
}

// Component-specific colors for dark theme
object DarkComponentColors {
    val ButtonSecondary = Color(0xFFB0BEC5)

}

// Create a container class to hold all theme-specific colors
data class AppColors(
    // Material theme colors
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val error: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onError: Color,

    // Custom component colors
    val backgroundGradientStatusBar: Color,
    val backgroundGradientFirst: Color,
    val backgroundGradientSecond: Color,

    val authTextFieldPrimary: Color,
    val authTextFieldSecondary: Color,
    val authTextFieldBackground: Color,

    val authButtonPrimary: Color,
    val authButtonBackground: Color,

    val authContentPrimary: Color,
    val authContentBackground: Color,

    val tabBarSelectedPrimary: Color,
    val tabBarSelectedSecondary: Color,
    val tabBarSelectedBackground: Color,

    val homeItemPrimary: Color,
    val homeItemBackground: Color,
    val homeTextPrimary: Color,
    val homeTextSecondary: Color,

    val cardGameContainerBackground: Color,
    val podiumRankGameBackground: Color,
    val buttonGameBackground: Color
)


// Create theme-specific instances
val LightAppColors = AppColors(
    // Material theme colors
    primary = Color(0xFFF8F3F8),
    primaryVariant = Color.Black,
    secondary = Color(0xFF595D99),
    background = Color(0xFFE7E8FF),
    surface = Color.White,
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,

    // Component colors
    backgroundGradientStatusBar = LightComponentColors.BackgroundGradientStatusBar,
    backgroundGradientFirst = LightComponentColors.BackgroundGradientFirst,
    backgroundGradientSecond = LightComponentColors.BackgroundGradientSecond,

    authTextFieldPrimary = LightComponentColors.AuthTextFieldPrimary,
    authTextFieldSecondary = LightComponentColors.AuthTextFieldSecondary,
    authTextFieldBackground = LightComponentColors.AuthTextFieldBackground,

    authButtonPrimary = LightComponentColors.AuthButtonPrimary,
    authButtonBackground = LightComponentColors.AuthButtonBackground,

    authContentPrimary = LightComponentColors.AuthContentPrimary,
    authContentBackground = LightComponentColors.AuthContentBackground,

    tabBarSelectedPrimary = LightComponentColors.TabBarSelectedPrimary,
    tabBarSelectedSecondary = LightComponentColors.TabBarSelectedSecondary,
    tabBarSelectedBackground = LightComponentColors.TabBarSelectedBackground,

    homeItemPrimary = LightComponentColors.HomeItemPrimary,
    homeItemBackground = LightComponentColors.HomeItemBackground,
    homeTextPrimary = LightComponentColors.HomeTextPrimary,
    homeTextSecondary = LightComponentColors.HomeTextSecondary,

    cardGameContainerBackground = LightComponentColors.CardGameContainerBackground,
    podiumRankGameBackground = LightComponentColors.PodiumRankGameBackground,
    buttonGameBackground = LightComponentColors.ButtonGameBackground
)



val DarkAppColors = AppColors(
    // Material theme colors
    primary = Color(0xFFF8F3F8),
    primaryVariant = Color.Black,
    secondary = Color(0xFF595D99),
    background = Color(0xFFE7E8FF),
    surface = Color.White,
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,

    //
    backgroundGradientStatusBar = LightComponentColors.BackgroundGradientStatusBar,
    backgroundGradientFirst = LightComponentColors.BackgroundGradientFirst,
    backgroundGradientSecond = LightComponentColors.BackgroundGradientSecond,

    authTextFieldPrimary = LightComponentColors.AuthTextFieldPrimary,
    authTextFieldSecondary = LightComponentColors.AuthTextFieldSecondary,
    authTextFieldBackground = LightComponentColors.AuthTextFieldBackground,

    authButtonPrimary = LightComponentColors.AuthButtonPrimary,
    authButtonBackground = LightComponentColors.AuthButtonBackground,

    authContentPrimary = LightComponentColors.AuthContentPrimary,
    authContentBackground = LightComponentColors.AuthContentBackground,

    tabBarSelectedPrimary = LightComponentColors.TabBarSelectedPrimary,
    tabBarSelectedSecondary = LightComponentColors.TabBarSelectedSecondary,
    tabBarSelectedBackground = LightComponentColors.TabBarSelectedBackground,

    homeItemPrimary = LightComponentColors.HomeItemPrimary,
    homeItemBackground = LightComponentColors.HomeItemBackground,
    homeTextPrimary = LightComponentColors.HomeTextPrimary,
    homeTextSecondary = LightComponentColors.HomeTextSecondary,

    cardGameContainerBackground = LightComponentColors.CardGameContainerBackground,
    podiumRankGameBackground = LightComponentColors.PodiumRankGameBackground,
    buttonGameBackground = LightComponentColors.ButtonGameBackground
)
