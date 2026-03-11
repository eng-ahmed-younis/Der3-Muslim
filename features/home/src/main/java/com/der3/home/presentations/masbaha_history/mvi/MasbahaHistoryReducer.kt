package com.der3.home.presentations.masbaha_history.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class MasbahaHistoryReducer @Inject constructor() : Reducer<MasbahaHistoryAction, MasbahaHistoryState> {
    override fun reduce(action: MasbahaHistoryAction, state: MasbahaHistoryState): MasbahaHistoryState {
        return when (action) {
            is MasbahaHistoryAction.OnLoading -> state.copy(isLoading = action.isLoading)
            is MasbahaHistoryAction.OnHistoryLoaded -> state.copy(
                recentActivity = action.history,
                totalTasbihCount = action.totalCount,
                continuousDays = action.continuousDays,
                isLoading = false
            )
            is MasbahaHistoryAction.OnFilterChanged -> state.copy(selectedFilter = action.filter)
            is MasbahaHistoryAction.OnError -> state.copy(
                error = action.message,
                isLoading = false
            )
            is MasbahaHistoryAction.OnHistoryCleared -> state.copy(
                recentActivity = emptyList(),
                totalTasbihCount = 0,
                continuousDays = 0,
                isLoading = false
            )
        }
    }
}
