package com.der3.home.presentations.zekr_details.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviIntent

@Stable
sealed interface ZekrDetailsIntent : MviIntent {
    data class LoadZekrDetails(val zekrId: Int) : ZekrDetailsIntent
    data object Back : ZekrDetailsIntent

    data object IncrementZekrReadingCount : ZekrDetailsIntent

    data class ToggleAudio(val audioPath: String) : ZekrDetailsIntent
    data object StopAudio : ZekrDetailsIntent


    data class ExpandDropdownMenu(val isExpand: Boolean) : ZekrDetailsIntent

    data class FontSizeSheetVisibility(val isVisible: Boolean) : ZekrDetailsIntent

    data class UpdateFontSize(val updateFont: Int): ZekrDetailsIntent

}