package com.der3.home.presentations.side_menu.about_der3.mvi

import com.der3.mvi.MviIntent

sealed class AboutDer3Intent : MviIntent {
    object RateApp : AboutDer3Intent()
    object ShareApp : AboutDer3Intent()
    object ContactUs : AboutDer3Intent()
    object VisitWebsite : AboutDer3Intent()
    object PrivacyPolicy : AboutDer3Intent()
    object OpenSocialMedia : AboutDer3Intent()
    object Back : AboutDer3Intent()
    object Retry : AboutDer3Intent()
    object DismissError : AboutDer3Intent()
}
