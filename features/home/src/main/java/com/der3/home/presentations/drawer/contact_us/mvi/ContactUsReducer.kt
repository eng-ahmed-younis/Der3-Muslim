package com.der3.home.presentations.drawer.contact_us.mvi

import com.der3.mvi.Reducer

class ContactUsReducer : Reducer<ContactUsAction, ContactUsState> {
    override fun reduce(action: ContactUsAction, state: ContactUsState): ContactUsState {
        return when (action) {
            is ContactUsAction.UpdateName -> {
                state.copy(name = action.name)
            }

            is ContactUsAction.UpdateEmail -> {
                state.copy(email = action.email)
            }

            is ContactUsAction.UpdateMessage -> {
                state.copy(message = action.message)
            }

            is ContactUsAction.UpdateLoading -> {
                state.copy(isLoading = action.isLoading)
            }

            is ContactUsAction.UpdateError -> {
                state.copy(error = action.error)
            }
        }
    }
}
