package com.der3.home.presentations.zekr_details.mvi

import androidx.compose.runtime.Stable
import com.der3.home.domain.model.ZekrUiModel
import com.der3.mvi.MviAction

@Stable
sealed interface ZekrDetailsAction : MviAction {

    data class OnLoading(val isLoading: Boolean) : ZekrDetailsAction

    data class OnZekrDetailsLoaded(val zekrDetails: ZekrUiModel) : ZekrDetailsAction
    data object UpdateZekrReadingCount : ZekrDetailsAction

    data class UpdateAudioState(val state: AzkarAudioState) : ZekrDetailsAction
    data class OnError(val message: String) : ZekrDetailsAction
}