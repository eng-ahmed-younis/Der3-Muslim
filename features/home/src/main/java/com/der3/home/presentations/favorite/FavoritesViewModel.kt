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
import com.der3.screens.Screens
import com.der3.shared.domain.use_case.GetAllFavouritesUsecase
import com.der3.shared.domain.use_case.RemoveFromFavoriteUseCase
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
    private val getAllFavorites: GetAllFavouritesUsecase,
    private val removeFromFavorite: RemoveFromFavoriteUseCase,
    private val toggleAudio: ToggleAzkarAudioUseCase,
    private val observeAudioState: ObserveAzkarAudioStateUseCase,
    private val reducer: FavoritesReducer
) : MviBaseViewModel<FavoritesState, FavoritesAction, FavoritesIntent>(
    initialState = FavoritesState(),
    reducer = reducer
) {

    init {
        loadFavorites()
        
        observeAudioState()
            .onEach { /* can handle audio state if needed */ }
            .launchIn(viewModelScope)
    }


    override fun handleIntent(intent: FavoritesIntent) {
        when (intent) {
            FavoritesIntent.LoadFavorites -> {
                loadFavorites()
            }

            is FavoritesIntent.RemoveFromFavorite -> {
                viewModelScope.launch {
                    try {
                        onAction(FavoritesAction.OnLoading(true))
                        removeFromFavorite(intent.zekrId)
                        onAction(FavoritesAction.OnLoading(false))
                    } catch (e: Exception) {
                        onAction(FavoritesAction.OnLoading(false))
                        onEffect(MviEffect.OnErrorDialog(UiText.DynamicError(e.message ?: "Error removing favorite")))
                    }
                }
            }
            is FavoritesIntent.ToggleAudio -> {
                toggleAudio.invoke(intent.audioPath)
            }

            is FavoritesIntent.OnSearchQueryChanged -> {
                onAction(FavoritesAction.OnSearchQueryChanged(intent.query))
            }
            is FavoritesIntent.OnZekrClick -> {
                onEffect(MviEffect.Navigate(Der3NavigationRoute.ZekrDetailsScreen(
                    zekrId = intent.zekrId,
                    categoryId = -1 // Use -1 or a special ID to indicate it's from Favorites
                )))
            }

            FavoritesIntent.NavigateToAzkar -> {
                onEffect(MviEffect.Navigate(screen = Der3NavigationRoute.AllAzkarCategoriesScreen))
            }
        }

    }


    private fun loadFavorites() {
        getAllFavorites()
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
}
