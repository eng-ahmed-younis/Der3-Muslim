package com.der3.home.presentations.home_screen.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviState


@Stable
data class HomeState (
    val isLoading: Boolean = false
) : MviState