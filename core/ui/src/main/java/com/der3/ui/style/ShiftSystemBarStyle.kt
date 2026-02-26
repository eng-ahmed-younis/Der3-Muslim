package com.der3.ui.style

import android.os.Build
import android.view.View
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.der3.ui.themes.AppColors

@Composable
fun ShiftSystemBarStyle(
    statusBarColor: Color = AppColors.white,
    navigationBarColor: Color = AppColors.white,

    useDarkStatusBarIcons: Boolean = true,
    useDarkNavigationBarIcons: Boolean = true,

    isStatusBarVisible: Boolean = true,
    isNavigationBarVisible: Boolean = true,

    isEdgeToEdgeEnabled: Boolean = false,

    isStatusBarTransparent: Boolean = false,     // default: NOT transparent
    isNavigationBarTransparent: Boolean = false  // default: NOT transparent
) {
    val activity = (LocalContext.current as? ComponentActivity) ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val window = activity.window
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)

        if (isStatusBarVisible) insetsController.show(WindowInsets.Type.statusBars())
        else insetsController.hide(WindowInsets.Type.statusBars())

        if (isNavigationBarVisible) insetsController.show(WindowInsets.Type.navigationBars())
        else {
            insetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            insetsController.hide(WindowInsets.Type.navigationBars())
        }

        // STATUS BAR
        val sbBg = if (isStatusBarTransparent) Color.Transparent else statusBarColor
        val statusBarStyle =
            if (useDarkStatusBarIcons) SystemBarStyle.light(sbBg.toArgb(), sbBg.toArgb())
            else SystemBarStyle.dark(sbBg.toArgb())

        // NAV BAR
        val nbBg = if (isNavigationBarTransparent) Color.Transparent else navigationBarColor
        val navigationBarStyle =
            if (useDarkNavigationBarIcons) SystemBarStyle.light(nbBg.toArgb(), nbBg.toArgb())
            else SystemBarStyle.dark(nbBg.toArgb())

        activity.enableEdgeToEdge(
            statusBarStyle = statusBarStyle,
            navigationBarStyle = navigationBarStyle
        )

        val drawBehind = isEdgeToEdgeEnabled || isStatusBarTransparent || isNavigationBarTransparent
        WindowCompat.setDecorFitsSystemWindows(window, !drawBehind)
    }
}
