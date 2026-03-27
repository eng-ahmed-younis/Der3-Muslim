package com.der3.home.presentations.notification.mvi

import androidx.compose.runtime.Immutable
import com.der3.home.domain.model.NotificationItem
import com.der3.mvi.MviState


@Immutable
data class NotificationState(
    val isLoading: Boolean = false,
    val todayNotifications: List<NotificationItem> = emptyList(),
    val yesterdayNotifications: List<NotificationItem> = emptyList(),
    val ayaOfTheDay: String? = null,
    val error: String? = null
): MviState
