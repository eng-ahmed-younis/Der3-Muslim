package com.der3.home.presentations.drawer.share_app.mvi

import com.der3.mvi.MviIntent

sealed interface ShareAppIntent : MviIntent {
    data class ShareViaWhatsApp(val link: String) : ShareAppIntent
    data class ShareViaTelegram(val link: String) : ShareAppIntent
    data class ShareViaFacebook(val link: String) : ShareAppIntent
    data class ShareViaTwitter(val link: String) : ShareAppIntent
    data class CopyLink(val link: String) : ShareAppIntent
    data object Retry : ShareAppIntent
    data object DismissError : ShareAppIntent
    data object Back : ShareAppIntent
}
