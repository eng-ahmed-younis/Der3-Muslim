package com.der3.on_boarding.presentation.screens.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviState


@Stable
data class OnBoardingState (
    val currentPage: Int = 0,
    val totalPages: Int = 3,
    val isLoading: Boolean = false,
    val error: String? = null,
    val onBoardingCompleted: Boolean = false
): MviState