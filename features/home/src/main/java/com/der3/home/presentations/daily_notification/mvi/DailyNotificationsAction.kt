package com.der3.home.presentations.daily_notification.mvi

import androidx.compose.runtime.Stable
import com.der3.home.domain.DailyNotificationItem
import com.der3.mvi.MviAction

@Stable
sealed interface DailyNotificationsAction : MviAction {

    data class ToggleNotification(val id: Int) : DailyNotificationsAction
    data class DeleteNotification(val id: Int) : DailyNotificationsAction
    data class AddNotification(val notification: DailyNotificationItem) : DailyNotificationsAction
    data class UpdateNotification(val notification: DailyNotificationItem) : DailyNotificationsAction
}