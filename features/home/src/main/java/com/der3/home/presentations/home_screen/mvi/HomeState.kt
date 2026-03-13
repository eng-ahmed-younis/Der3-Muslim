package com.der3.home.presentations.home_screen.mvi

import androidx.compose.runtime.Immutable
import com.der3.mvi.MviState
import com.der3.ui.models.CategoryUi


@Immutable
data class HomeState (
    val isLoading: Boolean = false,
    val homeAzkarCategory : List<CategoryUi> = emptyList(),
    val error: String? = null
) : MviState