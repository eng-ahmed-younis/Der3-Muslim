package com.der3.home.presentations.home_screen.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviAction

@Stable
sealed interface HomeAction  : MviAction {
}