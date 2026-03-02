package com.der3.home.presentations.zekr_details

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.der3.data.params.ZekrDetailsParams
import com.der3.data.use_case.GetAzkarCategoryByIdUseCase
import com.der3.data.use_case.GetAzkarItemByIdUseCase
import com.der3.home.data.mappers.toZekrUiModel
import com.der3.home.data.mappers.toZekrUiModels
import com.der3.home.di.factory.ZekrDetailsViewModelFactory
import com.der3.home.domain.use_case.ObserveAzkarAudioStateUseCase
import com.der3.home.domain.use_case.StopAzkarAudioUseCase
import com.der3.home.domain.use_case.ToggleAzkarAudioUseCase
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsAction
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsIntent
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsReducer
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsState
import com.der3.model.UiText
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.mvi.MviEffect.Navigate
import com.der3.screens.Screens.Back
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@HiltViewModel(assistedFactory = ZekrDetailsViewModelFactory::class)
class ZekrDetailsViewModel @AssistedInject constructor(
    @Assisted private val params: ZekrDetailsParams,
    private val getAzkarItemByIdUseCase: GetAzkarItemByIdUseCase,
    private val toggleAudio: ToggleAzkarAudioUseCase,
    private val stopAudio: StopAzkarAudioUseCase,
    private val observeAudioState: ObserveAzkarAudioStateUseCase
) : MviBaseViewModel<ZekrDetailsState, ZekrDetailsAction, ZekrDetailsIntent>(
    initialState = ZekrDetailsState(),
    reducer = ZekrDetailsReducer()
) {

    init {
        getZekrDetails()
        // Observe audio player state and push it into MVI state
        observeAudioState()
            .onEach { state ->
                Log.i("ZekrDetailsReducer","init isplaying ${state.isPlaying}")
                onAction(ZekrDetailsAction.UpdateAudioState(state))
            }
            .catch { e ->
                onAction(ZekrDetailsAction.OnError(e.message ?: "Audio error"))
            }
            .launchIn(viewModelScope)
    }

    override fun handleIntent(intent: ZekrDetailsIntent) {
        when (intent) {

            ZekrDetailsIntent.Back -> {
                // optional: stop audio when leaving
                stopAudio.invoke()
                onEffect(Navigate(Back()))
            }

            is ZekrDetailsIntent.ToggleAudio -> {
                Log.i("ZekrDetailsViewModel", "ToggleAudio: ${intent.audioPath}")
                toggleAudio.invoke(intent.audioPath)
                // no need to manually update state here because observeAudioState() will emit
            }

            ZekrDetailsIntent.StopAudio -> {
                stopAudio.invoke()
            }

            is ZekrDetailsIntent.LoadZekrDetails -> {
                // load your zekr item here (repo/usecase) and keep its audio path
                // then UI can call ToggleAudio(audioPath)
            }

            ZekrDetailsIntent.IncrementZekrReadingCount -> {
                onAction(action = ZekrDetailsAction.UpdateZekrReadingCount)
            }
        }
    }

    private fun getZekrDetails() {
        getAzkarItemByIdUseCase.invoke(
            itemId = params.zekrId,
            categoryId = params.categoryId
        ).onStart {
            onAction(ZekrDetailsAction.OnLoading(true))
        }.map { zekr ->
            zekr?.toZekrUiModel()
        }.onEach {
            it?.let {
                onAction(ZekrDetailsAction.OnZekrDetailsLoaded(zekrDetails = it))
            }
        }.onCompletion {
            onAction(ZekrDetailsAction.OnLoading(false))
        }.catch {
            onEffect(
                MviEffect.OnErrorDialog(
                    error = UiText.DynamicError(
                        it.message ?: "Error"
                    )
                )
            )
            onAction(ZekrDetailsAction.OnLoading(false))
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        stopAudio.invoke()
    }
}