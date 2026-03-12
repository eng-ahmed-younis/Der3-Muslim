package com.der3.screens

import kotlinx.serialization.Serializable

@Serializable
sealed interface Der3NavigationRoute : Screens {

    // bottom bar
    @Serializable
    object HomeScreen : Der3NavigationRoute

    @Serializable
    object SectionScreen : Der3NavigationRoute

    @Serializable
    object QiblaScreen : Der3NavigationRoute

    @Serializable
    object TasbeehScreen : Der3NavigationRoute

    @Serializable
    object FavouriteScreen : Der3NavigationRoute




    // daily notifications
    @Serializable
    object DailyNotificationsScreen : Der3NavigationRoute

    @Serializable
    object AddCustomReminderScreen : Der3NavigationRoute


    // splash & onboarding
    @Serializable
    object SplashScreen : Der3NavigationRoute

    @Serializable
    object OnboardingScreen : Der3NavigationRoute



    // home screens
    @Serializable
    object AllCategoriesScreen : Der3NavigationRoute

    @Serializable
    data class CategoryDetailsScreen(
        val categoryId: Int,
        val categoryTitle: String,
        val categorySubtitle: String,
        val categoryCount: String
    ) : Der3NavigationRoute

    @Serializable
    data class ZekrDetailsScreen(
        val zekrId: Int,
        val categoryId: Int
    ) : Der3NavigationRoute


    @Serializable
    object SettingsScreen : Der3NavigationRoute

    @Serializable
    object MasbahaScreen : Der3NavigationRoute

    @Serializable
    object MasbahaHistoryScreen : Der3NavigationRoute

    @Serializable
    object AzkarScreen : Der3NavigationRoute

    @Serializable
    object AboutScreen : Der3NavigationRoute



}