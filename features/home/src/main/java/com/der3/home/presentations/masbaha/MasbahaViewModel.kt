package com.der3.home.presentations.masbaha

import androidx.lifecycle.viewModelScope
import com.der3.home.di.factory.MasbahaViewModelFactory
import com.der3.home.presentations.masbaha.mvi.MasbahaAction
import com.der3.home.presentations.masbaha.mvi.MasbahaIntent
import com.der3.home.presentations.masbaha.mvi.MasbahaReducer
import com.der3.home.presentations.masbaha.mvi.MasbahaState
import com.der3.model.SyncState
import com.der3.model.UiText
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.player.audio.api.AudioPlayer
import com.der3.screens.Screens
import com.der3.shared.domain.use_case.masbaha.GetLocalMasbahaZekrSize
import com.der3.shared.domain.use_case.masbaha.GetMasbahaAzkarsUseCase
import com.der3.shared.domain.use_case.masbaha.SyncMasbahaDataUseCase
import com.der3.shared.params.MasbahaParams
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


@HiltViewModel(assistedFactory = MasbahaViewModelFactory::class)
class MasbahaViewModel @AssistedInject constructor(
    @Assisted private val masbahaParams: MasbahaParams,
    private val getMasbahaAzkarsUseCase: GetMasbahaAzkarsUseCase,
    private val syncMasbahaDataUseCase: SyncMasbahaDataUseCase,
    private val getLocalMasbahaZekrSize: GetLocalMasbahaZekrSize,
    private val audioPlayer: AudioPlayer,
    reducer: MasbahaReducer
) : MviBaseViewModel<MasbahaState, MasbahaAction, MasbahaIntent>(
    initialState = MasbahaState(),
    reducer = reducer
) {

    init {
        loadAzkars()
        onAction(MasbahaAction.SetShowBackButton(show = masbahaParams.openFromSection))
    }

    override fun handleIntent(intent: MasbahaIntent) {
        when (intent) {
            is MasbahaIntent.OnBackClick -> {
               onEffect(MviEffect.Navigate(screen = Screens.Back()))
            }

            is MasbahaIntent.SelectAzkar -> {
                onAction(MasbahaAction.SetSelectedAzkar(intent.azkar))
            }

            is MasbahaIntent.IncrementCount -> {
                if (viewState.isSoundEnabled) {
                    audioPlayer.play(viewState.clickAudioPath)
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

            MasbahaIntent.Retry -> {
                loadAzkars()
            }

            MasbahaIntent.DismissError -> {
                onAction(MasbahaAction.DismissError)
            }
        }
    }


    private fun loadAzkars() {
        getLocalMasbahaZekrSize.invoke()
            .onStart {
             //   onAction(MasbahaAction.OnLoading(true))
            }
            .onEach { size ->
                if (size > 0) {
                    // If local data exists, load it
                    getMasbahaAzkarsUseCase()
                        .onEach { azkars ->
                            onAction(MasbahaAction.OnAzkarsLoaded(azkars))
                        }
                        .catch { e ->
                            onAction(MasbahaAction.OnError(e.message ?: "Unknown Error"))
                        }
                        .launchIn(viewModelScope)
                } else {
                    // If no local data, sync from remote
                    syncMasbahaDataUseCase()
                        .onEach { syncState ->
                        when (syncState) {
                            SyncState.SYNCING -> onAction(MasbahaAction.OnLoading(true))
                            SyncState.SUCCESS -> {
                                getMasbahaAzkarsUseCase()
                                    .onEach { azkars ->
                                        onAction(MasbahaAction.OnAzkarsLoaded(azkars))
                                    }
                                    .catch { e ->
                                        onAction(
                                            MasbahaAction.OnError(
                                                e.message ?: "Unknown Error"
                                            )
                                        )
                                    }
                                    .launchIn(viewModelScope)
                                onAction(MasbahaAction.OnLoading(false))
                            }

                            SyncState.FAILED -> {
                                onEffect(
                                    MviEffect.OnErrorDialog(
                                        error = UiText.DynamicError(message = "Failed to sync data. Please check your connection and try again.")
                                    )
                                )
                            }

                            else -> {}
                        }
                    }
                }
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