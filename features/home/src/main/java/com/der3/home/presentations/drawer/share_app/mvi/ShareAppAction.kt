package com.der3.home.presentations.drawer.share_app.mvi

import com.der3.mvi.MviAction

sealed interface ShareAppAction : MviAction {
    data object ShowLoading : ShareAppAction
    data class ShowError(val message: String) : ShareAppAction
    data class GetDownloadLink(val link: String) : ShareAppAction
    data object DismissError : ShareAppAction
}
