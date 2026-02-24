package com.der3.ui.themes

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.graphics.drawable.DrawableCompat.getLayoutDirection
import com.der3.model.AppStyle
import com.der3.ui.language.LocalAppLanguage
import com.der3.ui.language.LocalDirection
import java.util.Locale



@Composable
fun Der3MuslimTheme(
    style: AppStyle = AppStyle.LIGHT,
    language: Locale = LocalAppLanguage.current,
    content: @Composable () -> Unit
) {

    Log.i("currentLanguage", "currentLanguage: $language")
    val colors = when (style) {
        AppStyle.LIGHT -> lightColors
        AppStyle.DARK -> darkColors
        AppStyle.SYSTEM -> if (isSystemInDarkTheme()) darkColors
        else lightColors
    }
  //  val typography = buildTypography(language)

    CompositionLocalProvider(
        LocalCurrentColor provides colors,
       // LocalAppTypography provides typography,
      //  LocalAppRoundedCornerShape provides roundShapes,
        LocalAppLanguage provides language,
       // LocalDirection provides getLayoutDirection(mapLocaleToShiftLanguage(language)),
      //  LocalDrawerState provides rememberDrawerState(initialValue = DrawerValue.Closed),
        content = content
    )
}