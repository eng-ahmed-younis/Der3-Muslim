package com.der3.home.presentations.favorite.mvi

import com.der3.mvi.MviIntent

sealed class FavoritesIntent : MviIntent {
    data object LoadFavorites : FavoritesIntent()
    data class RemoveFromFavorite(val zekrId: Int) : FavoritesIntent()
    data class ToggleAudio(val audioPath: String) : FavoritesIntent()
    data class OnZekrClick(val zekrId: Int) : FavoritesIntent()
    data class OnSearchQueryChanged(val query: String) : FavoritesIntent()
    data object NavigateToAzkar : FavoritesIntent()
}
