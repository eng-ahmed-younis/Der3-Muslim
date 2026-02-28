package com.der3.home.presentations.daily_notification.mvi

import androidx.compose.runtime.Stable
import com.der3.home.domain.DailyNotificationItem
import com.der3.mvi.MviIntent

@Stable
sealed interface DailyNotificationsIntent  : MviIntent {
    object LoadNotifications : DailyNotificationsIntent
    data class ToggleNotification(val id: Int) : DailyNotificationsIntent
    data class DeleteNotification(val id: Int) : DailyNotificationsIntent
    data class AddNotification(val notification: DailyNotificationItem) : DailyNotificationsIntent
    data class UpdateNotification(val notification: DailyNotificationItem) : DailyNotificationsIntent




    data object NavigateBack : DailyNotificationsIntent
    data object NavigateToEditNotification : DailyNotificationsIntent
    data object NavigateToAddNotification : DailyNotificationsIntent
    data object NavigateToAddCustomReminder : DailyNotificationsIntent
}