package com.der3.home.presentations.masbaha

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Build
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
import com.der3.utils.connectivity.ConnectivityObserver
import com.der3.utils.connectivity.NetworkState
import com.der3.utils.connectivity.NetworkStatus
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


@HiltViewModel(assistedFactory = MasbahaViewModelFactory::class)
class MasbahaViewModel @AssistedInject constructor(
    @Assisted private val masbahaParams: MasbahaParams,
    @ApplicationContext private val context: Context,
    private val connectivityObserver: ConnectivityObserver,
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
        observeNetwork()
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
            is MasbahaIntent.ShowInternetRequiredDialog -> {
                onAction(MasbahaAction.ShowInternetRequiredDialog(show = intent.show))
            }

            MasbahaIntent.OpenNetworkSettings -> {
                openNetworkSetting()

            }

            MasbahaIntent.RefreshAfterBack -> {
                if (viewState.azkars.isEmpty() || viewState.networkState == NetworkState.Available) {
                    loadAzkars()
                }
            }
        }
    }


    private fun loadAzkars() {
        getLocalMasbahaZekrSize.invoke()
            .onEach { size ->
                if (size > 0) {
                    fetchLocalAzkars()
                } else {
                    handleFirstTimeSync()
                }
            }
            .catch { e ->
                onAction(MasbahaAction.OnError(e.message ?: "Unknown Error"))
            }
            .launchIn(viewModelScope)
    }

    private fun handleFirstTimeSync() {
        if (viewState.networkState !in listOf(NetworkState.Available)) {
            onAction(MasbahaAction.ShowInternetRequiredDialog(show = true))
            return
        }

        // If not offline, proceed with sync
        syncMasbahaDataUseCase()
            .onEach { syncState ->
                when (syncState) {
                    SyncState.SYNCING -> onAction(MasbahaAction.OnLoading(true))
                    SyncState.SUCCESS -> {
                        fetchLocalAzkars()
                        onAction(MasbahaAction.OnLoading(false))
                    }

                    SyncState.FAILED -> {
                        onAction(MasbahaAction.OnLoading(false))
                        onEffect(
                            MviEffect.OnErrorDialog(
                                error = UiText.DynamicError(message = "Failed to sync data. Please check your connection.")
                            )
                        )
                    }

                    else -> {}
                }
            }
            .catch { e ->
                onAction(MasbahaAction.OnLoading(false))
                onAction(MasbahaAction.OnError(e.message ?: "Sync Failed"))
            }
            .launchIn(viewModelScope)
    }




    private fun fetchLocalAzkars() {
        getMasbahaAzkarsUseCase()
            .onEach { azkars ->
                onAction(MasbahaAction.OnAzkarsLoaded(azkars))
            }
            .catch { e ->
                onAction(MasbahaAction.OnError(e.message ?: "Error loading data"))
            }
            .launchIn(viewModelScope)
    }

    private fun observeNetwork() {
        NetworkStatus.observeAsState(
            connectivityObserver = connectivityObserver,
            scope = viewModelScope
        ).onEach { state ->
            onAction(
                MasbahaAction.OnNetworkStatusChanged(status = state)
            )
            if (state == NetworkState.Available && viewState.azkars.isEmpty()) {
                loadAzkars()
            }
        }.launchIn(viewModelScope)
    }

    private fun openNetworkSetting(){
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Opens the "Internet Connectivity" slice panel (floating UI)
            Intent(android.provider.Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
        } else {
            // Fallback for older Android versions to full Wireless Settings
            Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)
        }.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        
    }
}