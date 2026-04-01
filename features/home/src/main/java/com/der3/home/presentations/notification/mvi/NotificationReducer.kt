package com.der3.home.presentations.notification.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class NotificationReducer @Inject constructor() : Reducer<NotificationAction, NotificationState> {
    override fun reduce(action: NotificationAction, state: NotificationState): NotificationState {
        return when (action) {
            is NotificationAction.Loading -> {
                state.copy(
                    isLoading = action.isLoading
                )
            }
            is NotificationAction.LoadNotificationSuccess -> state.copy(
                isLoading = false,
                todayNotifications = action.today,
                yesterdayNotifications = action.yesterday,
                ayaOfTheDay = action.aya,
                error = null
            )
            is NotificationAction.Error -> state.copy(
                isLoading = false,
                error = action.message
            )
            is NotificationAction.DismissError -> state.copy(
                error = null
            )
        }
    }
}
