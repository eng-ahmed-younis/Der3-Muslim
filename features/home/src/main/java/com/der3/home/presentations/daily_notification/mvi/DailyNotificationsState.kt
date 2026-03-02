package com.der3.home.presentations.daily_notification.mvi

import androidx.compose.runtime.Immutable
import com.der3.home.domain.model.DailyNotificationItem
import com.der3.home.domain.model.mockItems
import com.der3.mvi.MviState
import com.der3.utils.DEFAULT_BOOLEAN

@Immutable
data class DailyNotificationsState(
    val isLoading: Boolean = DEFAULT_BOOLEAN,
    val notifications: List<DailyNotificationItem> = mockItems,
    val error: String? = null
): MviState