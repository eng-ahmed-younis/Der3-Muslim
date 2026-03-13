package com.der3.home.utils

import android.content.Context
import androidx.compose.material.icons.Icons
import com.der3.home.domain.model.ShareItem
import com.der3.model.ShareAppType
import com.der3.ui.R


object PlayStoreLink {
    fun getLink(context: Context): String {
        return "https://play.google.com/store/apps/details?id=${context.packageName}"
    }

    const val WATTS_APP_PACKAGE = "com.whatsapp"
    const val TELEGRAM_PACKAGE = "org.telegram.messenger"
    const val FACEBOOK_PACKAGE = "com.facebook.katana"
    const val TWITTER_PACKAGE = "com.twitter.android"

}
val shareTypeItems = mutableListOf<ShareItem>().apply {
    add(
        ShareItem(
            type = ShareAppType.WHATS_APP,
            label = R.string.whatsapp,
            icon = R.drawable.whatsapp
        )
    )
    add(
        ShareItem(
            type = ShareAppType.TELEGRAM,
            label = R.string.telegram,
            icon = R.drawable.telegrm
        )
    )

    add(
        ShareItem(
            type = ShareAppType.FACEBOOK,
            label = R.string.facebook,
            icon = R.drawable.facebook
        )
    )

    add(
        ShareItem(
            type = ShareAppType.TWITTER,
            label = R.string.twitter,
            icon = R.drawable.twitter
        )
    )

}