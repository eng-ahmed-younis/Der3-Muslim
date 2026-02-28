package com.der3.home.presentations.custom_reminder.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class AddCustomReminderReducer @Inject constructor()
    : Reducer<AddCustomReminderAction,AddCustomReminderState> {
    override fun reduce(
        action: AddCustomReminderAction,
        state: AddCustomReminderState
    ): AddCustomReminderState {
        TODO("Not yet implemented")
    }
}