package com.der3.home.presentations.notification.mvi

import com.der3.mvi.MviIntent

sealed class NotificationIntent : MviIntent {
    object LoadNotifications : NotificationIntent()
    object Retry : NotificationIntent()
    object Back : NotificationIntent()
    object DismissError : NotificationIntent()
}
