package com.der3.ui.themes

import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


val AppColors: Colors
    @Composable get() = LocalCurrentColor.current

val AppTypography: Typography
    @Composable get() = LocalAppTypography.current

val shapes: Shapes
    @Composable get() = LocalAppRoundedCornerShape.current

val isDarkTheme : Boolean
    @Composable get() = LocalDarkTheme.current

val isStatusBarDark: Boolean
    @Composable get() = LocalIsStatusBarDark.current

internal val LocalCurrentColor = staticCompositionLocalOf<Colors> {
    error("No colors provided")
}
internal val LocalAppTypography = staticCompositionLocalOf<Typography> {
    error("No font provided")
}
internal val LocalAppRoundedCornerShape = staticCompositionLocalOf<Shapes> {
    error("No shape provided")
}

internal val LocalIsStatusBarDark = staticCompositionLocalOf<Boolean> {
    error("No status bar dark provided")
}

internal val LocalDarkTheme = staticCompositionLocalOf<Boolean> {
   error("No dark theme provided")
}
