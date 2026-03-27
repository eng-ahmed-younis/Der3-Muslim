package com.der3.home.presentations.side_menu.rate_us.mvi

import com.der3.mvi.MviState
import com.der3.model.UiText

data class RateUsState(
    val isLoading: Boolean = false,
    val error: UiText? = null
) : MviState
