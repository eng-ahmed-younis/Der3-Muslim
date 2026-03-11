package com.der3.home.presentations.masbaha_history.mvi

import com.der3.model.HistoryFilter
import com.der3.mvi.MviAction
import com.der3.shared.data.source.local.entity.MasbahaHistoryEntity

sealed interface MasbahaHistoryAction : MviAction {
    data class OnHistoryLoaded(
        val history: List<MasbahaHistoryEntity>,
        val totalCount: Int,
        val continuousDays: Int
    ) : MasbahaHistoryAction
    data class OnFilterChanged(val filter: HistoryFilter) : MasbahaHistoryAction
    data class OnLoading(val isLoading: Boolean) : MasbahaHistoryAction
    data class OnError(val message: String) : MasbahaHistoryAction
}
