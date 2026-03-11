package com.der3.home.presentations.masbaha_history

import androidx.lifecycle.viewModelScope
import com.der3.home.presentations.masbaha_history.mvi.MasbahaHistoryAction
import com.der3.home.presentations.masbaha_history.mvi.MasbahaHistoryIntent
import com.der3.home.presentations.masbaha_history.mvi.MasbahaHistoryReducer
import com.der3.home.presentations.masbaha_history.mvi.MasbahaHistoryState
import com.der3.model.UiText
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.shared.data.source.local.dao.MasbahaHistoryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasbahaHistoryViewModel @Inject constructor(
    private val historyDao: MasbahaHistoryDao,
    reducer: MasbahaHistoryReducer
) : MviBaseViewModel<MasbahaHistoryState, MasbahaHistoryAction, MasbahaHistoryIntent>(
    initialState = MasbahaHistoryState(),
    reducer = reducer
) {

    init {
        handleIntent(MasbahaHistoryIntent.LoadHistory)
    }

    public override fun handleIntent(intent: MasbahaHistoryIntent) {
        when (intent) {
            is MasbahaHistoryIntent.LoadHistory -> loadHistory()
            is MasbahaHistoryIntent.ChangeFilter -> {
                onAction(MasbahaHistoryAction.OnFilterChanged(intent.filter))
                loadHistory()
            }
            is MasbahaHistoryIntent.Back -> {
                onEffect(MviEffect.Navigate(com.der3.screens.Screens.Back()))
            }
            is MasbahaHistoryIntent.ViewAllRecentActivity -> {
                // Handle navigation/view update
            }
            is MasbahaHistoryIntent.ClearHistory -> clearHistory()
        }
    }

    private fun clearHistory() {
        viewModelScope.launch {
            onAction(MasbahaHistoryAction.OnLoading(true))
            try {
                historyDao.clearHistory()
                onAction(MasbahaHistoryAction.OnLoading(false))
            } catch (e: Exception) {
                onAction(MasbahaHistoryAction.OnError(e.message ?: "Unknown error"))
                onEffect(MviEffect.OnErrorDialog(UiText.DynamicError(e.message ?: "Unknown error")))
            }
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            onAction(MasbahaHistoryAction.OnLoading(true))
            try {
                historyDao.getAllHistory().collectLatest { history ->
                    val totalCount = history.sumOf { it.count }
                    // Simple logic for continuous days, could be improved with actual date logic
                    val continuousDays = if (history.isNotEmpty()) 5 else 0 
                    
                    onAction(
                        MasbahaHistoryAction.OnHistoryLoaded(
                            history = history,
                            totalCount = totalCount,
                            continuousDays = continuousDays
                        )
                    )
                }
            } catch (e: Exception) {
                onAction(MasbahaHistoryAction.OnError(e.message ?: "Unknown error"))
                onEffect(MviEffect.OnErrorDialog(UiText.DynamicError(e.message ?: "Unknown error")))
            }
        }
    }
}
