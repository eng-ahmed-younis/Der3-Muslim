package com.der3.home.presentations.zekr_details.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviIntent

@Stable
sealed interface ZekrDetailsIntent : MviIntent {
    data class LoadZekrDetails(val zekrId: Int) : ZekrDetailsIntent
    data object Back : ZekrDetailsIntent
}