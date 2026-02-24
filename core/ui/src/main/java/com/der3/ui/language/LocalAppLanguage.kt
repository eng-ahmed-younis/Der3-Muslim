package com.der3.ui.language

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.LayoutDirection
import java.util.Locale

val LocalAppLanguage = staticCompositionLocalOf<Locale> {
    Locale.getDefault()
}

val LocalDirection = staticCompositionLocalOf<LayoutDirection> {
    error("No layout direction provided")
}
