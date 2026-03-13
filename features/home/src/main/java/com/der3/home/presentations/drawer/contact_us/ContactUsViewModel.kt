package com.der3.home.presentations.drawer.contact_us

import com.der3.home.presentations.drawer.contact_us.mvi.ContactUsAction
import com.der3.home.presentations.drawer.contact_us.mvi.ContactUsIntent
import com.der3.home.presentations.drawer.contact_us.mvi.ContactUsReducer
import com.der3.home.presentations.drawer.contact_us.mvi.ContactUsState
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor() :
    MviBaseViewModel<ContactUsState, ContactUsAction, ContactUsIntent>(
        initialState = ContactUsState(),
        reducer = ContactUsReducer()
    ) {

    override fun handleIntent(intent: ContactUsIntent) {
        when (intent) {
            is ContactUsIntent.OnNameChange -> {
                onAction(ContactUsAction.UpdateName(name = intent.name))
            }

            is ContactUsIntent.OnEmailChange -> {
                onAction(ContactUsAction.UpdateEmail(email = intent.email))
            }

            is ContactUsIntent.OnMessageChange -> {
                onAction(ContactUsAction.UpdateMessage(intent.message))
            }


            ContactUsIntent.SendMessage -> {
                onAction(ContactUsAction.UpdateLoading(true))
                // Mocking network call
                // ... logic to send message ...
                onAction(ContactUsAction.UpdateLoading(false))
            }
            ContactUsIntent.Back -> {
                onEffect(MviEffect.Navigate(screen = Screens.Back()))
            }
            ContactUsIntent.DismissError -> onAction(ContactUsAction.UpdateError(null))
            ContactUsIntent.Retry -> {
                onAction(ContactUsAction.UpdateLoading(true))
                // ... retry logic ...
                onAction(ContactUsAction.UpdateLoading(false))
            }
            ContactUsIntent.OpenWebsite -> {
                // Handle open website
            }
            ContactUsIntent.SendEmail -> {
                // Handle send email
            }
            ContactUsIntent.OpenSocialMedia -> {
                // Handle social media
            }
            ContactUsIntent.ShareApp -> {
                // Handle share app
            }
        }
    }
}
