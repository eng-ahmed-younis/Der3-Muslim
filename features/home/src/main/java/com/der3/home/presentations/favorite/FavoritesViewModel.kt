package com.der3.home.presentations.favorite

import androidx.lifecycle.viewModelScope
import com.der3.home.data.mappers.toZekrUiModel
import com.der3.home.domain.use_case.ObserveAzkarAudioStateUseCase
import com.der3.home.domain.use_case.ToggleAzkarAudioUseCase
import com.der3.home.presentations.favorite.mvi.FavoritesAction
import com.der3.home.presentations.favorite.mvi.FavoritesIntent
import com.der3.home.presentations.favorite.mvi.FavoritesReducer
import com.der3.home.presentations.favorite.mvi.FavoritesState
import com.der3.model.UiText
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
import com.der3.shared.data.source.local.entity.RecycleBinEntity
import com.der3.shared.domain.use_case.fav.GetAllFavouritesUsecase
import com.der3.shared.domain.use_case.fav.RemoveFromFavoriteUseCase
import com.der3.shared.domain.use_case.recycler.InsertToRecycleBinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllFavoritesUseCase: GetAllFavouritesUsecase,
    private val removeFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val insertToRecycleBinUseCase: InsertToRecycleBinUseCase,
    private val toggleAudioUseCase: ToggleAzkarAudioUseCase,
    private val observeAudioState: ObserveAzkarAudioStateUseCase,
    private val reducer: FavoritesReducer
) : MviBaseViewModel<FavoritesState, FavoritesAction, FavoritesIntent>(
    initialState = FavoritesState(),
    reducer = reducer
) {

    init {
        loadFavorites()

        observeAudioState()
            .onEach { state ->
                onAction(FavoritesAction.OnPlaybackStateChanged(state.isPlaying, state.currentPath))
            }
            .launchIn(viewModelScope)
    }


    override fun handleIntent(intent: FavoritesIntent) {
        when (intent) {
            FavoritesIntent.LoadFavorites -> {
                loadFavorites()
            }

            is FavoritesIntent.RemoveFromFavorite -> {
                removeFromFavorite(intent.zekrId)
            }

            is FavoritesIntent.ToggleAudio -> {
                toggleAudioUseCase.invoke(intent.audioPath)
            }

            is FavoritesIntent.OnSearchQueryChanged -> {
                onAction(FavoritesAction.OnSearchQueryChanged(intent.query))
            }

            is FavoritesIntent.OnZekrClick -> {
                onEffect(
                    MviEffect.Navigate(
                        Der3NavigationRoute.ZekrDetailsScreen(
                            zekrId = intent.zekrId,
                            categoryId = intent.categoryId
                        )
                    )
                )
            }

            FavoritesIntent.NavigateToAzkar -> {
                onEffect(MviEffect.Navigate(screen = Der3NavigationRoute.AllAzkarCategoriesScreen))
            }

            FavoritesIntent.OpenRecycleBin -> {
                onEffect(MviEffect.Navigate(screen = Der3NavigationRoute.RecycleBinScreen))
            }
        }

    }


    private fun loadFavorites() {
        getAllFavoritesUseCase()
            .onStart {
                onAction(FavoritesAction.OnLoading(true))
            }
            .onEach { entities ->
                val uiModels = entities.map { it.toZekrUiModel() }
                onAction(FavoritesAction.OnFavoritesLoaded(uiModels))
            }
            .catch { e ->
                onEffect(MviEffect.OnErrorDialog(UiText.DynamicError(e.message ?: "Unknown Error")))
            }.onCompletion {
                onAction(FavoritesAction.OnLoading(false))
            }
            .launchIn(viewModelScope)
    }


    private fun removeFromFavorite(zekrId: Int) {
        viewModelScope.launch {
            try {
                onAction(FavoritesAction.OnLoading(true))
                val favorite = viewState.favorites.find { it.id == zekrId }
                removeFavoriteUseCase.invoke(id = zekrId)
                favorite?.let { fav ->
                    insertToRecycleBinUseCase.invoke(
                        item = RecycleBinEntity(
                            id = fav.id,
                            categoryId = fav.categoryId ?: -1,
                            text = fav.text,
                            audioPath = fav.audioPath,
                            repeatCount = fav.repeatCount,
                            categoryName = fav.categoryName,
                            deletedAt = System.currentTimeMillis()
                        )
                    )
                }
                onAction(FavoritesAction.OnLoading(false))
            } catch (e: Exception) {
                onAction(FavoritesAction.OnLoading(false))
                onEffect(
                    MviEffect.OnErrorDialog(
                        UiText.DynamicError(
                            e.message ?: "Error removing favorite"
                        )
                    )
                )
            }
        }
    }
}
