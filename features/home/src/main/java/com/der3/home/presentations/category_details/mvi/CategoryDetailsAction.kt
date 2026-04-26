package com.der3.home.presentations.category_details.mvi

import androidx.compose.runtime.Stable
import com.der3.home.domain.model.ZekrUiModel
import com.der3.mvi.MviAction
import com.der3.player.audio.model.AzkarAudioState

@Stable
sealed interface CategoryDetailsAction : MviAction {

    data class OnLoading(val isLoading: Boolean) : CategoryDetailsAction

    data class OnCategoryLoaded(val azkars: List<ZekrUiModel>) : CategoryDetailsAction

    data class UpdateAudioState(val audioState: AzkarAudioState) : CategoryDetailsAction

    data class SetPlayingZekrId(val zekrId: Int?) : CategoryDetailsAction

    data class GetCategoryParams(
        val categoryId: Int,
        val categoryTitle: String,
        val categorySubtitle: String,
        val categoryCount: String
    ) : CategoryDetailsAction
}