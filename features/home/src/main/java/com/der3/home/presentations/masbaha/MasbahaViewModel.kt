package com.der3.home.presentations.masbaha

import androidx.lifecycle.viewModelScope
import com.der3.home.presentations.masbaha.mvi.MasbahaAction
import com.der3.home.presentations.masbaha.mvi.MasbahaIntent
import com.der3.home.presentations.masbaha.mvi.MasbahaReducer
import com.der3.home.presentations.masbaha.mvi.MasbahaState
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.player.audio.api.AudioPlayer
import com.der3.shared.domain.use_case.GetMasbahaAzkarsUseCase
import com.der3.shared.domain.use_case.SyncMasbahaDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasbahaViewModel @Inject constructor(
    private val getMasbahaAzkarsUseCase: GetMasbahaAzkarsUseCase,
    private val syncMasbahaDataUseCase: SyncMasbahaDataUseCase,
    private val audioPlayer: AudioPlayer,
    reducer: MasbahaReducer
) : MviBaseViewModel<MasbahaState, MasbahaAction, MasbahaIntent>(
    initialState = MasbahaState(),
    reducer = reducer
) {

    init {
        loadAzkars()
    }

    override fun handleIntent(intent: MasbahaIntent) {
        when (intent) {
            MasbahaIntent.Back -> {
                onEffect(MviEffect.Navigate(com.der3.screens.Screens.Back()))
            }
            is MasbahaIntent.SelectAzkar -> {
                onAction(MasbahaAction.SetSelectedAzkar(intent.azkar))
            }
            is MasbahaIntent.IncrementCount -> {
                if (viewState.isSoundEnabled) {
                    audioPlayer.play("raw/click.mp3")
                }
                onAction(MasbahaAction.UpdateCount)
            }
            MasbahaIntent.ResetCount -> {
                onAction(MasbahaAction.ResetCount)
            }
            is MasbahaIntent.SetTargetCount -> {
                onAction(MasbahaAction.UpdateTargetCount(intent.target))
            }
            is MasbahaIntent.ToggleAutoSwitch -> {
                onAction(MasbahaAction.UpdateAutoSwitch(intent.enabled))
            }
            is MasbahaIntent.ToggleVibration -> {
                onAction(MasbahaAction.UpdateVibration(intent.type))
            }
            is MasbahaIntent.ToggleSound -> {
                onAction(MasbahaAction.UpdateSound(intent.enabled))
            }
            is MasbahaIntent.OpenHistory -> {
                onEffect(MviEffect.Navigate(com.der3.screens.Der3NavigationRoute.MasbahaHistoryScreen))
            }
        }
    }

    private fun loadAzkars() {
        // 1. Observe from Room immediately (Single Source of Truth)
        // This ensures the user sees local data instantly without waiting for network
        getMasbahaAzkarsUseCase()
            .onStart {
                onAction(MasbahaAction.OnLoading(true))
                syncMasbahaDataUseCase()
            }
            .onEach { azkars ->
                onAction(MasbahaAction.OnAzkarsLoaded(azkars))
            }
            .catch { e ->
                onAction(MasbahaAction.OnError(e.message ?: "Unknown Error"))
            }
            .onCompletion {
                onAction(MasbahaAction.OnLoading(false))
            }
            .launchIn(viewModelScope)
    }
}