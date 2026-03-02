package com.der3.home.presentations.zekr_details.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class ZekrDetailsReducer @Inject constructor() : Reducer<ZekrDetailsAction,ZekrDetailsState> {
    override fun reduce(
        action: ZekrDetailsAction,
        state: ZekrDetailsState
    ): ZekrDetailsState {
        return when(action){
            is ZekrDetailsAction.LoadZekrDetails -> state.copy(isLoading = true)
            is ZekrDetailsAction.UpdateZekrCount -> {
                state
            }
        }
    }
}