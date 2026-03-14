package com.der3.home.presentations.favorite.mvi

import com.der3.home.domain.model.ZekrUiModel

import com.der3.mvi.MviState

data class FavoritesState(
    val isLoading: Boolean = false,
    val favorites: List<ZekrUiModel> = emptyList(),
    val filteredFavorites: List<ZekrUiModel> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
) : MviState
