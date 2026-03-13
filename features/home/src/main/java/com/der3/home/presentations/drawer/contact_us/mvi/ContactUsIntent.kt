package com.der3.home.presentations.drawer.contact_us.mvi

import com.der3.mvi.MviIntent

sealed class ContactUsIntent : MviIntent {
    data class OnNameChange(val name: String) : ContactUsIntent()
    data class OnEmailChange(val email: String) : ContactUsIntent()
    data class OnMessageChange(val message: String) : ContactUsIntent()
    object SendMessage : ContactUsIntent()
    object Back : ContactUsIntent()
    object Retry : ContactUsIntent()
    object DismissError : ContactUsIntent()
    object OpenWebsite : ContactUsIntent()
    object SendEmail : ContactUsIntent()
    object OpenSocialMedia : ContactUsIntent()
    object ShareApp : ContactUsIntent()
}
