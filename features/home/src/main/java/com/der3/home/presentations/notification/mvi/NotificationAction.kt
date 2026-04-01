package com.der3.home.presentations.notification.mvi

import com.der3.home.domain.model.NotificationItem
import com.der3.mvi.MviAction

sealed class NotificationAction : MviAction {
    data class Loading(val isLoading: Boolean) : NotificationAction()
    data class LoadNotificationSuccess(
        val today: List<NotificationItem>,
        val yesterday: List<NotificationItem>,
        val aya: String?
    ) : NotificationAction()
    data class Error(val message: String) : NotificationAction()
    object DismissError : NotificationAction()
}
