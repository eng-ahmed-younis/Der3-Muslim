package com.der3.home.presentations.recycle_bin.mvi

import androidx.compose.runtime.Immutable
import com.der3.home.domain.model.DeletedZekrUiModel
import com.der3.home.domain.model.ZekrUiModel
import com.der3.mvi.MviState

@Immutable
data class RecycleBinState(
    val isLoading: Boolean = false,
    val items: List<DeletedZekrUiModel> = emptyList(),
    val error: String? = null
) : MviState


