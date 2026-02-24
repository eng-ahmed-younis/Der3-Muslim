package com.der3.ui.themes

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color


internal val lightColors = Colors(
    green900 = Color(0xFF0D2B11),
    gold500 =  Color(0xFFC5A059),
)



internal val darkColors = Colors(
    green900 = Color(0xFF0D2B11),
    gold500 =  Color(0xFFC5A059),
)




@Stable
data class Colors(
    val green900: Color,
    val gold500: Color
)
