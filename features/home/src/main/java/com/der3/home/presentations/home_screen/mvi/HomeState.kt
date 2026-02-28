package com.der3.home.presentations.home_screen.mvi

import androidx.compose.runtime.Stable
import com.der3.data.model.AzkarCategory
import com.der3.mvi.MviState
import com.der3.ui.models.CategoryUi


@Stable
data class HomeState (
    val isLoading: Boolean = false,
    val homeAzkarCategory : List<CategoryUi> = emptyList()
) : MviState