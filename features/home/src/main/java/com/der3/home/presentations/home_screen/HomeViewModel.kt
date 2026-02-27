package com.der3.home.presentations.home_screen

import com.der3.home.presentations.home_screen.mvi.HomeAction
import com.der3.home.presentations.home_screen.mvi.HomeIntent
import com.der3.home.presentations.home_screen.mvi.HomeReducer
import com.der3.home.presentations.home_screen.mvi.HomeState
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
import javax.inject.Inject

class HomeViewModel @Inject constructor() : MviBaseViewModel<HomeState, HomeAction, HomeIntent>(
    initialState = HomeState(),
    reducer = HomeReducer()
) {
    override fun handleIntent(intent: HomeIntent) {
        when(intent){
            is HomeIntent.NavigateToDailyNotifications -> {
                onEffect(MviEffect.Navigate(Der3NavigationRoute.DailyNotificationsScreen))
            }
        }
    }
}