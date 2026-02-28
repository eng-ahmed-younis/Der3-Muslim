package com.der3.home.presentations.custom_reminder.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviIntent

@Stable
sealed interface AddCustomReminderIntent : MviIntent {

    data class UpdateName(val name: String) : AddCustomReminderIntent
    data class UpdateTime(val hour: Int, val minute: Int) : AddCustomReminderIntent
    data class UpdateRepeat(val repeat: Set<String>) : AddCustomReminderIntent
    data class UpdateSound(val sound: Boolean) : AddCustomReminderIntent
    data class UpdateVibration(val vibration: Boolean) : AddCustomReminderIntent
    data object SaveReminder : AddCustomReminderIntent
}