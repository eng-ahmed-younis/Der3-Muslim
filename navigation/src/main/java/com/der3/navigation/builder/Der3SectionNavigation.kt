package com.der3.navigation.builder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.der3.navigation.NavigationManager.navigateTo
import com.der3.screens.Der3NavigationRoute
import com.der3.sections.presentation.main_section.MainSectionRoute
import com.der3.sections.presentation.qibla.QiblaRoute


fun NavGraphBuilder.der3SectionNavigation(rootNavController: NavHostController) {

    composable<Der3NavigationRoute.SectionScreen> {
        MainSectionRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.QiblaScreen> {
        QiblaRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }
}
