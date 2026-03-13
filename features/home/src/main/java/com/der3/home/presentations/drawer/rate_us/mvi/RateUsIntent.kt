package com.der3.home.presentations.drawer.rate_us.mvi

import com.der3.mvi.MviIntent

sealed interface RateUsIntent : MviIntent {
    object RateNow : RateUsIntent
    object Dismiss : RateUsIntent
    object DismissError : RateUsIntent
    object Retry : RateUsIntent
}
