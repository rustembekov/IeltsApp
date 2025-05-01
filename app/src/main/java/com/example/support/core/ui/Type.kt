package com.example.support.core.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.support.R

// Set of Material typography styles to start with
val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.helvetica_neue_bold, FontWeight.Bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.helvetica_neue_bold, FontWeight.Bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.helvetica_neue_bold, FontWeight.Bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.protest_strike_regular, FontWeight.Normal)),
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.helvetica_neue_roman, FontWeight.Normal)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.helvetica_neue_roman, FontWeight.Normal)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.protest_strike_regular, FontWeight.Normal)),
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.protest_strike_regular, FontWeight.Normal)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.protest_strike_regular, FontWeight.Normal)),
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 0.sp
    ),

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)