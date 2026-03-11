package com.der3.home.presentations.masbaha_history.mvi

import com.der3.model.HistoryFilter
import com.der3.mvi.MviIntent

sealed interface MasbahaHistoryIntent : MviIntent {
    data object LoadHistory : MasbahaHistoryIntent
    data class ChangeFilter(val filter: HistoryFilter) : MasbahaHistoryIntent
    data object Back : MasbahaHistoryIntent
    data object ViewAllRecentActivity : MasbahaHistoryIntent
    data object ClearHistory : MasbahaHistoryIntent
}
