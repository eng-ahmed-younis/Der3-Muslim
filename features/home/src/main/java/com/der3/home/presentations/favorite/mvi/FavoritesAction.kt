package com.der3.home.presentations.favorite.mvi

import com.der3.home.domain.model.ZekrUiModel

import com.der3.mvi.MviAction

sealed class FavoritesAction : MviAction {
    data class OnLoading(val isLoading: Boolean) : FavoritesAction()
    data class OnFavoritesLoaded(val favorites: List<ZekrUiModel>) : FavoritesAction()
    data class OnSearchQueryChanged(val query: String) : FavoritesAction()
    data class OnError(val error: String?) : FavoritesAction()
    data class OnPlaybackStateChanged(val isPlaying: Boolean, val currentPath: String?) : FavoritesAction()
}
