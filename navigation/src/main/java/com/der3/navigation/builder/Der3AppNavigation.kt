package com.der3.navigation.builder

import AzkarDetailsRoute
import ZekrDetailsRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.der3.data.params.CategoryDetailsParams
import com.der3.home.presentations.all_categories.AllCategoriesRoute
import com.der3.home.presentations.custom_reminder.AddCustomReminderRoute
import com.der3.home.presentations.daily_notification.DailyNotificationsRoute
import com.der3.home.presentations.home_screen.HomeRoute
import com.der3.navigation.NavigationManager.navigateTo
import com.der3.on_boarding.presentation.screens.OnBoardingRoute
import com.der3.screens.Der3NavigationRoute
import com.der3.splash.presentation.IslamicSplashRoute


fun NavGraphBuilder.der3AppNavigation(rootNavController: NavHostController) {

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
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.AllCategoriesScreen> {
        AllCategoriesRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }





    composable<Der3NavigationRoute.DailyNotificationsScreen> {
        DailyNotificationsRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.AddCustomReminderScreen> {
        AddCustomReminderRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.CategoryDetailsScreen> { backStackEntry ->
        val param: Der3NavigationRoute.CategoryDetailsScreen = backStackEntry.toRoute()

        val screenParams = CategoryDetailsParams(
            categoryId = param.categoryId,
            categoryTitle = param.categoryTitle,
            categorySubtitle = param.categorySubtitle,
            categoryCount = param.categoryCount
        )


        AzkarDetailsRoute(
            params = screenParams
        ) { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.ZekrDetailsScreen> { backStackEntry ->
        val param: Der3NavigationRoute.ZekrDetailsScreen = backStackEntry.toRoute()

        ZekrDetailsRoute(

        ) { screen ->
            rootNavController.navigateTo(screen = screen)
        }
     }


}
