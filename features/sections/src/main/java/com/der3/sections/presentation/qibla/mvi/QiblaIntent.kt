package com.der3.sections.presentation.qibla.mvi

import com.der3.mvi.MviIntent

sealed interface QiblaIntent : MviIntent {
    object StartCompass : QiblaIntent
    object StopCompass : QiblaIntent
    object OnBackClick : QiblaIntent
    data class OnLocationChanged(val latitude: Double, val longitude: Double) : QiblaIntent
    data class OnDirectionUpdated(val qiblaDirection: Float, val compassRotation: Float) : QiblaIntent
}
