package com.der3.home.presentations.custom_reminde.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviState

@Stable
data class AddCustomReminderState (
    val isLoading: Boolean = false,
    val reminderName: String = "",
    val reminderTime: String = "",
    val reminderRepeat: String = "",
    val reminderSound: String = "",
    val reminderVibration: String = ""
) : MviState