package com.der3.home.presentations.drawer.rate_us.mvi

import com.der3.model.UiText
import com.der3.mvi.MviAction

sealed interface RateUsAction : MviAction {
    data class UpdateLoading(val isLoading: Boolean) : RateUsAction
    data class UpdateError(val error: UiText?) : RateUsAction
}
