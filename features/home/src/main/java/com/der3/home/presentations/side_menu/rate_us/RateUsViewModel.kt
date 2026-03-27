package com.der3.home.presentations.side_menu.rate_us

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import com.der3.home.presentations.side_menu.rate_us.mvi.RateUsAction
import com.der3.home.presentations.side_menu.rate_us.mvi.RateUsIntent
import com.der3.home.presentations.side_menu.rate_us.mvi.RateUsReducer
import com.der3.home.presentations.side_menu.rate_us.mvi.RateUsState
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class RateUsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) :
    MviBaseViewModel<RateUsState, RateUsAction, RateUsIntent>(
        initialState = RateUsState(),
        reducer = RateUsReducer()
    ) {

    override fun handleIntent(intent: RateUsIntent) {
        when (intent) {
            RateUsIntent.Dismiss -> {
                onEffect(MviEffect.Navigate(screen = Screens.Back()))
            }
            RateUsIntent.RateNow -> {

                val uri = "market://details?id=${context.packageName}".toUri()
                val goToMarket = Intent(Intent.ACTION_VIEW, uri).apply {
                    addFlags(
                        Intent.FLAG_ACTIVITY_NO_HISTORY or
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK or
                                Intent.FLAG_ACTIVITY_NEW_TASK
                    )
                }
                try {
                    context.startActivity(goToMarket)
                } catch (e: ActivityNotFoundException) {
                    // If Play Store is not installed, open in browser
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        "https://play.google.com/store/apps/details?id=${context.packageName}".toUri()
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(intent)
                }
            }
            RateUsIntent.DismissError -> {
                onAction(RateUsAction.UpdateError(null))
            }
            RateUsIntent.Retry -> {
                onAction(RateUsAction.UpdateError(null))
                handleIntent(RateUsIntent.RateNow)
            }
        }
    }
}
