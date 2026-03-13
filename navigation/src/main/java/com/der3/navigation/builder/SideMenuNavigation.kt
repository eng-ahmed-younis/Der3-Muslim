package com.der3.navigation.builder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.der3.home.presentations.drawer.about_der3.AboutDer3Route
import com.der3.home.presentations.drawer.contact_us.ContactUsRoute
import com.der3.home.presentations.drawer.share_app.ShareAppRoute
import com.der3.navigation.NavigationManager.navigateTo
import com.der3.screens.Der3NavigationRoute

fun NavGraphBuilder.sideMenuNavigation(rootNavController: NavHostController) {
    composable<Der3NavigationRoute.AboutScreen> {
        AboutDer3Route(
            onNavigate = { screen ->
                rootNavController.navigateTo(screen)
            }
        )
    }

    composable<Der3NavigationRoute.ContactScreen> {
        ContactUsRoute(
            onNavigate = { screen ->
                rootNavController.navigateTo(screen)
            }
        )
    }

    composable<Der3NavigationRoute.ShareScreen> {
        ShareAppRoute(
            onNavigate = { screen ->
                rootNavController.navigateTo(screen)
            }
        )
    }
}
