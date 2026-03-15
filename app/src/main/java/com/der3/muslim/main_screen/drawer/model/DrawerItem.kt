package com.der3.muslim.main_screen.drawer.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import com.der3.ui.R

data class DrawerItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: Screens
)


val drawerItems = listOf(
    DrawerItem(
        title = R.string.home_title,
        icon = Icons.Default.Home,
        route = Der3NavigationRoute.HomeScreen
    ),
    DrawerItem(
        title = R.string.settings_title,
        icon = Icons.Default.Settings,
        route = Der3NavigationRoute.SettingsScreen
    ),
    DrawerItem(
        title = R.string.about_title,
        icon = Icons.Default.Info,
        route = Der3NavigationRoute.AboutScreen
    ),
    DrawerItem(
        title = R.string.rate_title,
        icon = Icons.Default.Star,
        route = Der3NavigationRoute.RateScreen
    ),
    DrawerItem(
        title = R.string.share_title,
        icon = Icons.Default.Share,
        route = Der3NavigationRoute.ShareScreen
    ),
    DrawerItem(
        title = R.string.contact_title,
        icon = Icons.Default.Email,
        route = Der3NavigationRoute.ContactScreen
    ),
    DrawerItem(
        title = R.string.recycle_bin_title,
        icon = Icons.Default.Delete,
        route = Der3NavigationRoute.RecycleBinScreen
    ),
)