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
    object TasbeehScreen : Der3NavigationRoute

    @Serializable
    object FavouriteScreen : Der3NavigationRoute



    @Serializable
    object DailyNotificationsScreen : Der3NavigationRoute


    @Serializable
    object SplashScreen : Der3NavigationRoute

    @Serializable
    object OnboardingScreen : Der3NavigationRoute

    @Serializable
    object SettingsScreen : Der3NavigationRoute

    @Serializable
    object AzkarScreen : Der3NavigationRoute

    @Serializable
    object AboutScreen : Der3NavigationRoute

}