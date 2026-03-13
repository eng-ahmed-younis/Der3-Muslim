package com.der3.home.presentations.drawer.share_app

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
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
                shareApp(PlayStoreLink.FACEBOOK_PACKAGE)
            }

            is ShareAppIntent.ShareViaTelegram -> {
                shareApp(PlayStoreLink.TELEGRAM_PACKAGE)
            }

            is ShareAppIntent.ShareViaTwitter -> {
                shareApp(PlayStoreLink.TWITTER_PACKAGE)
            }

            is ShareAppIntent.ShareViaWhatsApp -> {
                shareApp(PlayStoreLink.WATTS_APP_PACKAGE)
            }
        }
    }

    private fun getDownloadLink() {
        onAction(ShareAppAction.GetDownloadLink(link = PlayStoreLink.getLink(context = context)))
    }

    private fun shareApp(packageName: String?) {
        val link = PlayStoreLink.getLink(context = context)
        val message = context.getString(com.der3.ui.R.string.share_message, link)

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
            packageName?.let {
                setPackage(it)
            }
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (android.os.Build.VERSION.SDK_INT >= 36) {
                removeLaunchSecurityProtection()
            }
        }

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "App not installed", Toast.LENGTH_SHORT).show()
        }
    }
}
