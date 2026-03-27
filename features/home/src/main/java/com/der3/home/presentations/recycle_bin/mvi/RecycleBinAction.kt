package com.der3.home.presentations.recycle_bin.mvi

import com.der3.home.domain.model.DeletedZekrUiModel
import com.der3.mvi.MviAction

sealed interface RecycleBinAction : MviAction {
    data class Loading(val isLoading: Boolean) : RecycleBinAction
    data class ItemsLoaded(val items: List<DeletedZekrUiModel>) : RecycleBinAction
    data class Error(val message: String?) : RecycleBinAction
    data object ClearError : RecycleBinAction
}
