package com.der3.home.presentations.side_menu.rate_us.mvi

import com.der3.mvi.Reducer

class RateUsReducer : Reducer<RateUsAction, RateUsState> {
    override fun reduce(action: RateUsAction, state: RateUsState): RateUsState {
        return when (action) {
            is RateUsAction.UpdateLoading -> state.copy(isLoading = action.isLoading)
            is RateUsAction.UpdateError -> state.copy(error = action.error)
        }
    }
}
