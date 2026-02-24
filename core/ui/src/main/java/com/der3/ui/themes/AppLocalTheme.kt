package com.der3.ui.themes

import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf


val AppColors: Colors
    @Composable get() = LocalCurrentColor.current

val AppTypography: Typography
    @Composable get() = LocalAppTypography.current

val shapes: Shapes
    @Composable get() = LocalAppRoundedCornerShape.current


internal val LocalCurrentColor = staticCompositionLocalOf<Colors> {
    error("No colors provided")
}
internal val LocalAppTypography = staticCompositionLocalOf<Typography> {
    error("No font provided")
}
internal val LocalAppRoundedCornerShape = staticCompositionLocalOf<Shapes> {
    error("No shape provided")
}