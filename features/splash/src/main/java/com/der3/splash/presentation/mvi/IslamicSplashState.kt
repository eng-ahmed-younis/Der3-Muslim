package com.der3.splash.presentation.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviState
import com.der3.utils.DEFAULT_BOOLEAN


@Stable
data class IslamicSplashState (
    val isLoading: Boolean = DEFAULT_BOOLEAN,
) : MviState