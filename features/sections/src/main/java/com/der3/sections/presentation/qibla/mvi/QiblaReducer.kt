package com.der3.sections.presentation.qibla.mvi

import com.der3.mvi.Reducer
import com.der3.sections.presentation.utils.qibla.bearingDifference
import javax.inject.Inject
import kotlin.math.abs

class QiblaReducer @Inject constructor() : Reducer<QiblaAction, QiblaState> {
    override fun reduce(action: QiblaAction, state: QiblaState): QiblaState {
        return when (action) {
            is QiblaAction.OnDirectionUpdated -> {
                val diff = bearingDifference(action.compassRotation, action.qiblaDirection)
                val isOnTarget = abs(diff) < 5f
                val guidance = when {
                    isOnTarget -> "أنت باتجاه القبلة الآن"
                    diff > 0 -> "دور قليلاً ناحية اليمين"
                    else -> "دور قليلاً ناحية اليسار"
                }
                state.copy(
                    qiblaDirection = action.qiblaDirection,
                    compassRotation = action.compassRotation,
                    directionText = getDirectionText(action.qiblaDirection),
                    guidanceText = guidance,
                    isOnTarget = isOnTarget
                )
            }
            is QiblaAction.OnLocationUpdated -> state.copy(
                currentLocationName = action.locationName,
                distanceToKaaba = action.distance
            )
            is QiblaAction.OnAccuracyChanged -> state.copy(
                isAccuracyLow = action.isLow
            )
            is QiblaAction.OnLoading -> state.copy(
                isLoading = action.isLoading
            )
            is QiblaAction.OnLocationChanged -> state // Logic handled in screen/viewmodel for now
        }
    }

    private fun getDirectionText(degrees: Float): String {
        val normalized = (degrees % 360 + 360) % 360
        return when (normalized) {
            in 337.5..360.0 -> "شمال"
            in 0.0..22.5 -> "شمال"
            in 22.5..67.5 -> "شمال شرق"
            in 67.5..112.5 -> "شرق"
            in 112.5..157.5 -> "جنوب شرق"
            in 157.5..202.5 -> "جنوب"
            in 202.5..247.5 -> "جنوب غرب"
            in 247.5..292.5 -> "غرب"
            in 292.5..337.5 -> "شمال غرب"
            else -> "شمال"
        }
    }
}
