package com.der3.home.presentations.custom_reminder

import com.der3.home.presentations.custom_reminder.mvi.AddCustomReminderAction
import com.der3.home.presentations.custom_reminder.mvi.AddCustomReminderIntent
import com.der3.home.presentations.custom_reminder.mvi.AddCustomReminderReducer
import com.der3.home.presentations.custom_reminder.mvi.AddCustomReminderState
import com.der3.mvi.MviBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCustomReminderViewModel @Inject constructor()
    : MviBaseViewModel<AddCustomReminderState, AddCustomReminderAction, AddCustomReminderIntent>(
        initialState = AddCustomReminderState(),
        reducer = AddCustomReminderReducer()
    ) {
    override fun handleIntent(intent: AddCustomReminderIntent) {
        when (intent) {
            is AddCustomReminderIntent.UpdateName -> {

            }

            AddCustomReminderIntent.SaveReminder -> {

            }
            is AddCustomReminderIntent.UpdateRepeat -> {}
            is AddCustomReminderIntent.UpdateSound -> {}
            is AddCustomReminderIntent.UpdateTime -> {}
            is AddCustomReminderIntent.UpdateVibration -> {}
        }
    }
}