package com.der3.home.presentations.side_menu.setting.mvi

import com.der3.mvi.MviState

data class SettingState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isNightMode: Boolean = false,
    val fontSize: Int = 18,
    val isAutoPlayEnabled: Boolean = true,
    val useDarkStatusBarIcons: Boolean = true,
    val playbackSpeed: Float = 1.0f,
    val isMorningRemindersEnabled: Boolean = true,
    val isEveningRemindersEnabled: Boolean = true,
    val isSleepingRemindersEnabled: Boolean = false,
    val morningReminderTime: String = "06:00 ص",
    val eveningReminderTime: String = "05:00 م",
    val sleepingReminderTime: String = "10:00 م"
) : MviState
