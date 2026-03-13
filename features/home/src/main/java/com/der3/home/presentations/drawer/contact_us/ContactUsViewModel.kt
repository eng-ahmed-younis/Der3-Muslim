package com.der3.home.presentations.drawer.contact_us

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.der3.home.presentations.drawer.contact_us.mvi.ContactUsAction
import com.der3.home.presentations.drawer.contact_us.mvi.ContactUsIntent
import com.der3.home.presentations.drawer.contact_us.mvi.ContactUsReducer
import com.der3.home.presentations.drawer.contact_us.mvi.ContactUsState
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) :
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
                openUrl("https://www.der3muslim.com")
            }
            ContactUsIntent.SendEmail -> {
                sendEmail()
            }
            ContactUsIntent.OpenSocialMedia -> {
                openUrl("https://twitter.com/der3muslim")
            }
            ContactUsIntent.ShareApp -> {
                onEffect(MviEffect.Navigate(screen = Der3NavigationRoute.ShareScreen))
            }
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No browser found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("support@der3muslim.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Inquiry from Der3 Muslim App")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
        }
    }
}
