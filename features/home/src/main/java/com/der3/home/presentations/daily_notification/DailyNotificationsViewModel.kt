package com.der3.home.presentations.daily_notification

import com.der3.home.presentations.daily_notification.mvi.DailyNotificationsAction
import com.der3.home.presentations.daily_notification.mvi.DailyNotificationsIntent
import com.der3.home.presentations.daily_notification.mvi.DailyNotificationsReducer
import com.der3.home.presentations.daily_notification.mvi.DailyNotificationsState
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyNotificationsViewModel @Inject constructor() :
    MviBaseViewModel<DailyNotificationsState, DailyNotificationsAction, DailyNotificationsIntent>(
        initialState = DailyNotificationsState(),
        reducer = DailyNotificationsReducer(),
    ) {

    override fun handleIntent(intent: DailyNotificationsIntent) {
        when (intent) {
            is DailyNotificationsIntent.AddNotification -> TODO()
            is DailyNotificationsIntent.DeleteNotification -> TODO()
            DailyNotificationsIntent.LoadNotifications -> TODO()
            DailyNotificationsIntent.NavigateBack -> {
                onEffect(MviEffect.Navigate(screen = Screens.Back()))
            }
            DailyNotificationsIntent.NavigateToAddNotification -> TODO()
            DailyNotificationsIntent.NavigateToEditNotification -> TODO()
            is DailyNotificationsIntent.ToggleNotification -> TODO()
            is DailyNotificationsIntent.UpdateNotification -> TODO()
        }
    }
}