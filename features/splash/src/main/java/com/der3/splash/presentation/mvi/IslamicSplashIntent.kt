package com.der3.splash.presentation.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviIntent


@Stable
sealed interface IslamicSplashIntent : MviIntent {

    data object LoadSplashScreen : IslamicSplashIntent

}