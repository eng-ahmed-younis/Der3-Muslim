package com.der3.home.presentations.side_menu.contact_us.mvi

import com.der3.mvi.MviAction

sealed class ContactUsAction : MviAction {
    data class UpdateName(val name: String) : ContactUsAction()
    data class UpdateEmail(val email: String) : ContactUsAction()
    data class UpdateMessage(val message: String) : ContactUsAction()
    data class UpdateLoading(val isLoading: Boolean) : ContactUsAction()
    data class UpdateError(val error: String?) : ContactUsAction()
}
