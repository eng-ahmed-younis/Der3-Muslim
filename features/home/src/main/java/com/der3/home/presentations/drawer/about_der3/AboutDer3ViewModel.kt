package com.der3.home.presentations.drawer.about_der3

import com.der3.home.presentations.drawer.about_der3.mvi.AboutDer3Action
import com.der3.home.presentations.drawer.about_der3.mvi.AboutDer3Intent
import com.der3.home.presentations.drawer.about_der3.mvi.AboutDer3Reducer
import com.der3.home.presentations.drawer.about_der3.mvi.AboutDer3State
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.Reducer
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutDer3ViewModel @Inject constructor() :
    MviBaseViewModel<AboutDer3State, AboutDer3Action, AboutDer3Intent>(
        initialState = AboutDer3State(),
        reducer = AboutDer3Reducer()
    ) {

    override fun handleIntent(intent: AboutDer3Intent) {
        when (intent) {
            AboutDer3Intent.Back -> {
                onEffect(MviEffect.Navigate(screen = Screens.Back()))
            }
            AboutDer3Intent.DismissError -> {
                onAction(AboutDer3Action.UpdateError(null))
            }
            AboutDer3Intent.Retry -> {
                // Example retry logic
                onAction(AboutDer3Action.UpdateLoading(true))
                // ... fetch data ...
                onAction(AboutDer3Action.UpdateLoading(false))
            }
            AboutDer3Intent.ContactUs -> {
                // Handle Contact Us
            }
            AboutDer3Intent.OpenSocialMedia -> {
                // Handle Social Media
            }
            AboutDer3Intent.PrivacyPolicy -> {
                // Handle Privacy Policy
            }
            AboutDer3Intent.RateApp -> {
                // Handle Rate App
            }
            AboutDer3Intent.ShareApp -> {
                // Handle Share App
            }
            AboutDer3Intent.VisitWebsite -> {
                // Handle Website
            }
        }
    }
}

