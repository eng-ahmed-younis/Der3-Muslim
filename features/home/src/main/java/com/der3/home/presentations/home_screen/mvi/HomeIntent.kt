package com.der3.home.presentations.home_screen.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviIntent

@Stable
sealed interface HomeIntent  : MviIntent {
}