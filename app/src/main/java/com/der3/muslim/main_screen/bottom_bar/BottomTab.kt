package com.der3.muslim.main_screen.bottom_bar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import com.der3.ui.R

data class BottomTab(
    val route: Screens,     // Using the Screens object for Type-Safety
    @StringRes val label: Int, // Using String Resource ID
    val icon: ImageVector
)

val bottomTabs = listOf(
    BottomTab(
        route = Der3NavigationRoute.HomeScreen,
        label = R.string.home_title,
        icon = Icons.Default.Home
    ),
    BottomTab(
        route = Der3NavigationRoute.SectionScreen,
        label = R.string.sections_title, // Added Section/Azkar tab
        icon = Icons.Default.Dashboard
    ),
    BottomTab(
        route = Der3NavigationRoute.TasbeehScreen,
        label = R.string.electronic_rosary_title,
        icon = Icons.Default.Fingerprint
    ),

    BottomTab(
        route = Der3NavigationRoute.FavouriteScreen,
        label = R.string.favorites_title,
        icon = Icons.Default.Favorite
    ),
)