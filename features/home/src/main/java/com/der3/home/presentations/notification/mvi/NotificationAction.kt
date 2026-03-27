package com.der3.home.presentations.notification.mvi

import com.der3.home.domain.model.NotificationItem
import com.der3.mvi.MviAction

sealed class NotificationAction : MviAction {
    object Loading : NotificationAction()
    data class Success(
        val today: List<NotificationItem>,
        val yesterday: List<NotificationItem>,
        val aya: String?
    ) : NotificationAction()
    data class Error(val message: String) : NotificationAction()
    object DismissError : NotificationAction()
}
