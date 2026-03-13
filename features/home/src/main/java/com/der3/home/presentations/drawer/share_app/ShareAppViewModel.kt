package com.der3.home.presentations.drawer.share_app

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.der3.home.presentations.drawer.share_app.mvi.ShareAppAction
import com.der3.home.presentations.drawer.share_app.mvi.ShareAppIntent
import com.der3.home.presentations.drawer.share_app.mvi.ShareAppReducer
import com.der3.home.presentations.drawer.share_app.mvi.ShareAppState
import com.der3.home.utils.PlayStoreLink
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareAppViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : MviBaseViewModel<ShareAppState, ShareAppAction, ShareAppIntent>(
    initialState = ShareAppState(),
    reducer = ShareAppReducer()
) {

    init {
        getDownloadLink()
    }

    override fun handleIntent(intent: ShareAppIntent) {
        when (intent) {
            is ShareAppIntent.Retry -> {
                getDownloadLink()
            }

            is ShareAppIntent.DismissError -> {
                onAction(ShareAppAction.DismissError)
            }

            is ShareAppIntent.Back -> {
                onEffect(MviEffect.Navigate(screen = Screens.Back()))
            }

            is ShareAppIntent.CopyLink -> {
                // Handle in UI or via Effect
            }

            is ShareAppIntent.ShareViaFacebook -> {
                // Handle in UI
            }

            is ShareAppIntent.ShareViaTelegram -> {
                // Handle in UI
            }

            is ShareAppIntent.ShareViaTwitter -> {
                // Handle in UI
            }

            is ShareAppIntent.ShareViaWhatsApp -> {
                // Handle in UI
            }
        }
    }

    private fun getDownloadLink() {
        onAction(ShareAppAction.GetDownloadLink(link = PlayStoreLink.getLink(context = context)))
    }
}
