package com.der3.home.presentations.masbaha_history.mvi

import com.der3.model.HistoryFilter
import com.der3.mvi.MviState
import com.der3.shared.data.source.local.entity.MasbahaHistoryEntity



data class MasbahaHistoryState(
    val isLoading: Boolean = false,
    val selectedFilter: HistoryFilter = HistoryFilter.DAY,
    val totalTasbihCount: Int = 0,
    val continuousDays: Int = 0,
    val recentActivity: List<MasbahaHistoryEntity> = emptyList(),
    val error: String? = null
) : MviState
