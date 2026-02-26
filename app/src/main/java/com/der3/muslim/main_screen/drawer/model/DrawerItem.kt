package com.der3.muslim.main_screen.drawer.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import com.der3.ui.R

data class DrawerItem(
    @StringRes val title: Int,
    @DrawableRes val icon: ImageVector,
    val route: Screens
)


val drawerItems = listOf(
    DrawerItem(
        title = R.string.home_title,
        icon = Icons.Default.Home,
        route = Der3NavigationRoute.HomeScreen
    ),
    DrawerItem(
        title = R.string.favorites_title,
        icon = Icons.Default.Favorite,
        route = Der3NavigationRoute.FavouriteScreen
    ),
    DrawerItem(
        title = R.string.azkar_title,
        icon = Icons.AutoMirrored.Filled.MenuBook,
        route = Der3NavigationRoute.AzkarScreen
    ),
    DrawerItem(
        title = R.string.electronic_rosary_title,
        icon = Icons.Default.Mosque,
        route = Der3NavigationRoute.TasbeehScreen
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
)