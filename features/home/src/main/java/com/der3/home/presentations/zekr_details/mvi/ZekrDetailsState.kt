package com.der3.home.presentations.zekr_details.mvi

import androidx.compose.runtime.Immutable
import com.der3.home.domain.ZekrUiModel
import com.der3.mvi.MviState


@Immutable
data class ZekrDetailsState (
    val isLoading: Boolean = false,
    val zekr: ZekrUiModel = ZekrUiModel.mock,
    val error: String? = null,
    val progress: Float = 0f,
    val currentCount: Int = 0,
    val totalCount: Int = 0,
): MviState