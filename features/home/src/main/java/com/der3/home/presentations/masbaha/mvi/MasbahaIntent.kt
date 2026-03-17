package com.der3.home.presentations.masbaha.mvi

import com.der3.mvi.MviIntent
import com.der3.shared.domain.model.MasbahaAzkar

sealed interface MasbahaIntent : MviIntent {
    data object OnBackClick : MasbahaIntent
    data class SelectAzkar(val azkar: MasbahaAzkar) : MasbahaIntent
    data object IncrementCount : MasbahaIntent
    data object ResetCount : MasbahaIntent
    data class SetTargetCount(val target: Int) : MasbahaIntent
    data class ToggleAutoSwitch(val enabled: Boolean) : MasbahaIntent
    data class ToggleVibration(val type: VibrationType) : MasbahaIntent
    data class ToggleSound(val enabled: Boolean) : MasbahaIntent
    data object OpenHistory : MasbahaIntent
    data object Retry : MasbahaIntent
    data object DismissError : MasbahaIntent
}