package com.der3.navigation.builder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.der3.home.presentations.daily_notification.DailyNotificationsRoute
import com.der3.home.presentations.home_screen.HomeRoute
import com.der3.navigation.NavigationManager.navigateTo
import com.der3.on_boarding.presentation.screens.OnBoardingRoute
import com.der3.screens.Der3NavigationRoute
import com.der3.splash.presentation.IslamicSplashRoute


fun NavGraphBuilder.mainNavigation(rootNavController: NavHostController) {

    composable<Der3NavigationRoute.SplashScreen> {
        IslamicSplashRoute {
            rootNavController.navigateTo(screen = it)
        }
    }

    composable<Der3NavigationRoute.OnboardingScreen> {
        OnBoardingRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.HomeScreen> {
        HomeRoute { screen ->
            rootNavController.navigate(route = screen)
        }
    }

    composable<Der3NavigationRoute.DailyNotificationsScreen> {
        DailyNotificationsRoute { screen ->
            rootNavController.navigate(route = screen)
        }
    }
}