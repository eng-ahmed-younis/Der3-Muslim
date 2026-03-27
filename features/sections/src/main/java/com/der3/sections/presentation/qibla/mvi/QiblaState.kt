package com.der3.sections.presentation.qibla.mvi

import androidx.compose.runtime.Immutable
import com.der3.mvi.MviState

@Immutable
data class QiblaState(
    val isLoading: Boolean = false,
    val qiblaDirection: Float = 0f,
    val compassRotation: Float = 0f,
    val currentLocationName: String = "جاري تحديد الموقع...",
    val distanceToKaaba: Double = 0.0,
    val directionText: String = "",
    val isAccuracyLow: Boolean = false,
    val guidanceText: String = "",
    val isOnTarget: Boolean = false
) : MviState
