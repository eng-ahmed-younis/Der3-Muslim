package com.der3.home.presentations.zekr_details.mvi

import androidx.compose.runtime.Immutable
import com.der3.home.domain.model.ZekrUiModel
import com.der3.mvi.MviState
import com.der3.player.audio.model.AzkarAudioState
import com.der3.ui.models.MenuItemData


@Immutable
data class ZekrDetailsState (
    val isLoading: Boolean = false,
    val zekrDetails: ZekrUiModel = ZekrUiModel.mock,
    val audioState: AzkarAudioState = AzkarAudioState(),
    val zekrPageSize : Int = 300,
    val zekrFontSize: Int = 0,
    val isMenuExpanded : Boolean = false,
    val fontSizeSheetVisibility: Boolean = false,
    val shareSheetVisibility: Boolean = false,
    val menuItems : List<MenuItemData> = com.der3.home.utils.menuItems,
    val audioPath: String = "",
    val error: String? = null,
    val progress: Float = 0f,
    val currentCount: Int = 0,
    val totalCount: Int = 0,
): MviState