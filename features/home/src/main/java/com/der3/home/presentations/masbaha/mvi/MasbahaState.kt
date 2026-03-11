package com.der3.home.presentations.masbaha.mvi

import androidx.compose.runtime.Immutable
import com.der3.mvi.MviState
import com.der3.shared.domain.model.MasbahaAzkar

@Immutable
data class MasbahaState(
    val isLoading: Boolean = false,
    val azkars: List<MasbahaAzkar> = emptyList(),
    val selectedAzkar: MasbahaAzkar? = null,
    val currentCount: Int = 0,
    val targetCount: Int = 33,
    val autoSwitch: Boolean = true,
    val isVibrationEnabled: Boolean = true,
    val isSoundEnabled: Boolean = true,
    val error: String? = null
) : MviState {
    val progress: Float
        get() = if (targetCount > 0) currentCount.toFloat() / targetCount else 0f
    
    val progressPercentage: Int
        get() = (progress * 100).toInt()
}