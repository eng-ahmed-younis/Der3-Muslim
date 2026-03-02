package com.der3.ui.models

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalDrawerState = staticCompositionLocalOf<DrawerState> {
    error("No DrawerState provided")
}


val drawerState: DrawerState
    @Composable get() = LocalDrawerState.current

