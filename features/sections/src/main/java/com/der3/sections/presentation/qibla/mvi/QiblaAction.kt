package com.der3.sections.presentation.qibla.mvi

import com.der3.mvi.MviAction

sealed interface QiblaAction : MviAction {
    data class OnDirectionUpdated(val qiblaDirection: Float, val compassRotation: Float) : QiblaAction
    data class OnLocationUpdated(val locationName: String, val distance: Double) : QiblaAction
    data class OnAccuracyChanged(val isLow: Boolean) : QiblaAction
    data class OnLoading(val isLoading: Boolean) : QiblaAction
    data class OnLocationChanged(val latitude: Double, val longitude: Double) : QiblaAction
}
