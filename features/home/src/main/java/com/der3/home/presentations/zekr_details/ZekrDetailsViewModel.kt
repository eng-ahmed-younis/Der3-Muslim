package com.der3.home.presentations.zekr_details

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.der3.shared.params.ZekrDetailsParams
import com.der3.shared.domain.use_case.GetAzkarItemByIdUseCase
import com.der3.data_store.api.DataStoreRepository
import com.der3.home.data.mappers.toZekrUiModel
import com.der3.home.di.factory.ZekrDetailsViewModelFactory
import com.der3.home.domain.use_case.ObserveAzkarAudioStateUseCase
import com.der3.home.domain.use_case.ResetAzkarAudioUseCase
import com.der3.home.domain.use_case.SetAzkarVolumeUseCase
import com.der3.home.domain.use_case.StopAzkarAudioUseCase
import com.der3.home.domain.use_case.ToggleAzkarAudioUseCase
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsAction
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsAction.*
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsIntent
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsReducer
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsState
import com.der3.model.UiText
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.mvi.MviEffect.Navigate
import com.der3.screens.Screens.Back
import com.der3.shared.data.source.local.entity.FavoriteEntity
import com.der3.shared.domain.use_case.fav.AddToFavoriteUseCase
import com.der3.shared.domain.use_case.fav.IsFavoriteUseCase
import com.der3.shared.domain.use_case.fav.RemoveFromFavoriteUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ZekrDetailsViewModelFactory::class)
class ZekrDetailsViewModel @AssistedInject constructor(
    @Assisted private val params: ZekrDetailsParams,
    private val getAzkarItemByIdUseCase: GetAzkarItemByIdUseCase,
    private val toggleAzkarAudioUseCase: ToggleAzkarAudioUseCase,
    private val stopAzkarAudioUseCase: StopAzkarAudioUseCase,
    private val resetAzkarAudioUseCase: ResetAzkarAudioUseCase,
    private val setAzkarVolumeUseCase: SetAzkarVolumeUseCase,
    private val observeAzkarAudioStateUseCase: ObserveAzkarAudioStateUseCase,
    private val dataStoreRepository: DataStoreRepository,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase
) : MviBaseViewModel<ZekrDetailsState, ZekrDetailsAction, ZekrDetailsIntent>(
    initialState = ZekrDetailsState(),
    reducer = ZekrDetailsReducer()
) {

    init {
        getZekrDetails()
        observeFavoriteStatus()
        // Observe audio player state and push it into MVI state
        observeAzkarAudioStateUseCase()
            .onEach { state ->
                Log.i("ZekrDetailsReducer", "init isplaying ${state.isPlaying}")
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
                stopAzkarAudioUseCase.invoke()
                onEffect(Navigate(Back()))
            }

            is ZekrDetailsIntent.ToggleAudio -> {
                Log.i("ZekrDetailsViewModel", "ToggleAudio: ${intent.audioPath}")
                toggleAzkarAudioUseCase.invoke(intent.audioPath)
                // no need to manually update state here because observeAudioState() will emit
            }

            ZekrDetailsIntent.StopAudio -> {
                stopAzkarAudioUseCase.invoke()
            }

            ZekrDetailsIntent.ResetAudio -> {
                resetAzkarAudioUseCase.invoke(
                    audioPath = viewState.zekrDetails.audioPath
                )
            }

            is ZekrDetailsIntent.LoadZekrDetails -> {
                // load your zekr item here (repo/usecase) and keep its audio path
                // then UI can call ToggleAudio(audioPath)
            }

            is ZekrDetailsIntent.IncrementZekrReadingCount -> {
                onAction(action = ZekrDetailsAction.UpdateZekrReadingCount)
            }

            is ZekrDetailsIntent.ExpandDropdownMenu -> {
                onAction(action = ZekrDetailsAction.ExpandDropdownMenu(expanded = intent.isExpand))
            }

            is ZekrDetailsIntent.UpdateFontSize -> {
                onAction(action = UpdateDeaultFontSize(font = intent.updateFont))
                dataStoreRepository.zekrScreenDetailsFontSize = intent.updateFont
            }

            is ZekrDetailsIntent.FontSizeSheetVisibility -> {
                onAction(action = FontSizeSheetVisibility(visible = intent.isVisible))
            }

            is ZekrDetailsIntent.VolumeSheetVisibility -> {
                onAction(action = ZekrDetailsAction.VolumeSheetVisibility(visible = intent.isVisible))
            }

            is ZekrDetailsIntent.UpdateVolume -> {
                onAction(action = ZekrDetailsAction.UpdateVolume(volume = intent.volume))
                setAzkarVolumeUseCase.invoke(intent.volume)
            }

            is ZekrDetailsIntent.ShareSheetVisibility -> {
                onAction(action = ShareSheetVisibility(visible = intent.isVisible))
            }

            ZekrDetailsIntent.ShareAsText -> {
                onAction(action = ShareSheetVisibility(visible = false))
                shareAsText()
            }

            ZekrDetailsIntent.ShareAsImage -> {
                onAction(action = ShareSheetVisibility(visible = false))
                onEffect(MviEffect.CaptureAndShareImage)
            }

            ZekrDetailsIntent.Retry -> {
                getZekrDetails()
            }

            ZekrDetailsIntent.DismissError -> {
                onAction(ZekrDetailsAction.ClearError)
            }

            ZekrDetailsIntent.ToggleFavorite -> {
                toggleFavoriteStatus()
            }
        }
    }

    private fun observeFavoriteStatus() {
        isFavoriteUseCase.invoke(params.zekrId)
            .onEach { isFavorite ->
                onAction(ZekrDetailsAction.UpdateFavorite(isFavorite))
            }
            .launchIn(viewModelScope)
    }

    private fun toggleFavoriteStatus() {
        viewModelScope.launch {
            val zekr = viewState.zekrDetails
            val isCurrentlyFavorite = zekr.isFavorite
            if (isCurrentlyFavorite) {
                removeFromFavoriteUseCase.invoke(zekr.id)
            } else {
                addToFavoriteUseCase.invoke(
                    FavoriteEntity(
                        id = zekr.id,
                        text = zekr.text,
                        audioPath = zekr.audioPath,
                        repeatCount = zekr.repeatCount,
                        categoryName = zekr.categoryName,
                        categoryId = zekr.categoryId ?: -1
                    )
                )
            }
            onAction(ZekrDetailsAction.UpdateFavorite(!isCurrentlyFavorite))
        }
    }

    private fun shareAsText() {
        val state = viewState
        val zekr = state.zekrDetails
        val currentCount = state.currentCount
        val totalCount = zekr.repeatCount

        val shareBody = buildString {
            append("✨ *((${zekr.text})) (${if (totalCount > 1) "يفعلُ ذلك $totalCount مرَّاتٍ" else "مرة واحدة"})* ✨\n\n")
            append("🔢 العدد المطلوب: $totalCount\n")
            append("✅ تم قراءة: $currentCount\n\n")
            append("📲 تمت المشاركة من تطبيق *درع المسلم*")
        }

        onEffect(MviEffect.Share(text = shareBody))
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
            loadDefaultFontSize()
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

    private fun loadDefaultFontSize() {
        onAction(
            action = ZekrDetailsAction.UpdateDeaultFontSize(font = dataStoreRepository.zekrScreenDetailsFontSize)
        )
    }

    override fun onCleared() {
        super.onCleared()
        stopAzkarAudioUseCase.invoke()
    }
}
