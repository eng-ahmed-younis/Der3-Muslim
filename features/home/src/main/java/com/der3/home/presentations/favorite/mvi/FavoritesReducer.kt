package com.der3.home.presentations.favorite.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

import com.der3.shared.utils.normalizeArabic

class FavoritesReducer @Inject constructor() : Reducer<FavoritesAction, FavoritesState> {
    override fun reduce(action: FavoritesAction, state: FavoritesState): FavoritesState {
        return when (action) {
            is FavoritesAction.OnLoading -> {
                state.copy(isLoading = action.isLoading)
            }
            is FavoritesAction.OnFavoritesLoaded -> {
                state.copy(
                    favorites = action.favorites,
                    filteredFavorites = action.favorites.filter {
                        it.text.normalizeArabic().contains(state.searchQuery.normalizeArabic(), ignoreCase = true)
                    },
                    isLoading = false
                )
            }
            is FavoritesAction.OnSearchQueryChanged -> {
                state.copy(
                    searchQuery = action.query,
                    filteredFavorites = state.favorites.filter {
                        it.text.normalizeArabic().contains(action.query.normalizeArabic(), ignoreCase = true)
                    }
                )
            }
            is FavoritesAction.OnError ->{
                state.copy(error = action.error, isLoading = false)
            }
            is FavoritesAction.OnPlaybackStateChanged -> {
                state.copy(
                    isPlaying = action.isPlaying,
                    currentPlayingPath = action.currentPath
                )
            }
        }
    }
}
