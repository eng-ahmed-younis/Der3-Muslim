package com.der3.navigation.builder

import com.der3.home.presentations.category_details.CategoryDetailsRoute
import com.der3.home.presentations.side_menu.about_der3.AboutDer3Route
import com.der3.home.presentations.zekr_details.ZekrDetailsRoute
import com.der3.home.presentations.side_menu.contact_us.ContactUsRoute
import com.der3.home.presentations.favorite.FavoritesRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.der3.home.presentations.notification.NotificationRoute
import com.der3.home.presentations.recycle_bin.RecycleBinRoute
import com.der3.shared.params.CategoryDetailsParams
import com.der3.shared.params.ZekrDetailsParams
import com.der3.home.presentations.azkar_category.AzkarCategoryRoute
import com.der3.home.presentations.custom_reminder.AddCustomReminderRoute
import com.der3.home.presentations.daily_notification.DailyNotificationsRoute
import com.der3.home.presentations.home_screen.HomeRoute
import com.der3.home.presentations.masbaha.MasbahaRoute
import com.der3.home.presentations.masbaha_history.MasbahaHistoryRoute
import com.der3.sections.presentation.main_section.MainSectionRoute
import com.der3.navigation.NavigationManager.navigateTo
import com.der3.on_boarding.presentation.screens.OnBoardingRoute
import com.der3.screens.Der3NavigationRoute
import com.der3.shared.params.MasbahaParams
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

    composable<Der3NavigationRoute.SectionScreen> {
        MainSectionRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.AllAzkarCategoriesScreen> {
        AzkarCategoryRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }





    composable<Der3NavigationRoute.DailyNotificationsScreen> {
        DailyNotificationsRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.NotificationScreen> {
        NotificationRoute{ screens ->
            rootNavController.navigateTo(screen = screens)
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


        CategoryDetailsRoute(
            params = screenParams
        ) { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.ZekrDetailsScreen> { backStackEntry ->
        val param: Der3NavigationRoute.ZekrDetailsScreen = backStackEntry.toRoute()

        val screenParams = ZekrDetailsParams(
            zekrId = param.zekrId,
            categoryId = param.categoryId
        )

        ZekrDetailsRoute(
            params = screenParams
        ) { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.TasbeehScreen> { backStackEntry ->
        val params: Der3NavigationRoute.TasbeehScreen = backStackEntry.toRoute()
        val masbahaParams = MasbahaParams(
            openFromSection = params.openFromSection
        )

        MasbahaRoute(
            params = masbahaParams
        ) { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.MasbahaHistoryScreen> {
        MasbahaHistoryRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.AboutScreen> {
        AboutDer3Route { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.ContactScreen> {
        ContactUsRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.FavouriteScreen> {
        FavoritesRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }

    composable<Der3NavigationRoute.RecycleBinScreen> {
        RecycleBinRoute { screen ->
            rootNavController.navigateTo(screen = screen)
        }
    }
}
