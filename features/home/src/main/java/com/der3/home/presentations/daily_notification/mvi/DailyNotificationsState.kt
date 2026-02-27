package com.der3.home.presentations.daily_notification.mvi

import androidx.compose.runtime.Stable
import com.der3.home.domain.DailyNotificationItem
import com.der3.home.domain.mockItems
import com.der3.mvi.MviState
import com.der3.utils.DEFAULT_BOOLEAN

@Stable
data class DailyNotificationsState(
    val isLoading: Boolean = DEFAULT_BOOLEAN,
    val notifications: List<DailyNotificationItem> = mockItems,
    val error: String? = null
): MviState