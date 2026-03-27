package com.der3.home.presentations.masbaha.mvi

import androidx.compose.runtime.Immutable
import com.der3.mvi.MviState
import com.der3.shared.domain.model.MasbahaAzkar
import com.der3.utils.connectivity.NetworkState

@Immutable
data class MasbahaState(
    val isLoading: Boolean = false,
    val azkars: List<MasbahaAzkar> = emptyList(),
    val selectedAzkar: MasbahaAzkar? = null,
    val currentCount: Int = 0,
    val targetCount: Int = 33,
    val autoSwitch: Boolean = true,
    val vibrationType: VibrationType = VibrationType.SHORT,
    val isSoundEnabled: Boolean = true,
    val error: String? = null,
    val clickAudioPath: String = "raw/click.mp3",
    val showBackButton: Boolean = false,
    val networkState: NetworkState = NetworkState.Idle,
    val showInternetRequiredDialog: Boolean = false
) : MviState {
    val progress: Float
        get() = if (targetCount > 0) currentCount.toFloat() / targetCount else 0f
    
    val progressPercentage: Int
        get() = (progress * 100).toInt()
}

enum class VibrationType {
    SHORT, LONG, HEARTBEAT, NONE
}
