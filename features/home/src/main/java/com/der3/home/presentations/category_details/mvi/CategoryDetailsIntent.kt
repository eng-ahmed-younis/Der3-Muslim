package com.der3.home.presentations.category_details.mvi

import androidx.compose.runtime.Stable
import com.der3.home.domain.ZekrUiModel
import com.der3.mvi.MviIntent

@Stable
sealed interface CategoryDetailsIntent  : MviIntent {

    data object OnBackClick : CategoryDetailsIntent
    data class OnShareClick(val zekr: ZekrUiModel) : CategoryDetailsIntent
    data class OnFavoriteClick(val zekr: ZekrUiModel) : CategoryDetailsIntent
    data class OnBookmarkClick(val zekr: ZekrUiModel) : CategoryDetailsIntent
    data class OnPlayClick(val zekr: ZekrUiModel) : CategoryDetailsIntent
    data class OnZekrClick(val zekrId: Int) : CategoryDetailsIntent

}