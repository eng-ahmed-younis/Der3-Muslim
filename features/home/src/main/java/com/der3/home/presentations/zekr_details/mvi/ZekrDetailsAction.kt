package com.der3.home.presentations.zekr_details.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviAction

@Stable
sealed interface ZekrDetailsAction : MviAction {
    data class LoadZekrDetails(val zekrId: Int) : ZekrDetailsAction
    data class UpdateZekrCount(val zekrId: Int, val count: Int) : ZekrDetailsAction
}