package com.der3.home.presentations.recycle_bin.mvi

import com.der3.mvi.Reducer

class RecycleBinReducer : Reducer<RecycleBinAction, RecycleBinState> {
    override fun reduce(action: RecycleBinAction, state: RecycleBinState): RecycleBinState {
        return when (action) {
            is RecycleBinAction.Loading -> state.copy(isLoading = action.isLoading, error = null)
            is RecycleBinAction.ItemsLoaded -> state.copy(isLoading = false, items = action.items, error = null)
            is RecycleBinAction.Error -> state.copy(isLoading = false, error = action.message)
            is RecycleBinAction.ClearError -> state.copy(error = null)
        }
    }
}
