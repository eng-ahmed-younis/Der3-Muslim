package com.der3.home.presentations.masbaha.mvi

import com.der3.mvi.MviAction
import com.der3.shared.domain.model.MasbahaAzkar

sealed interface MasbahaAction : MviAction {
    data class OnLoading(val isLoading: Boolean) : MasbahaAction
    data class OnAzkarsLoaded(val azkars: List<MasbahaAzkar>) : MasbahaAction
    data class OnError(val message: String) : MasbahaAction
    data object UpdateCount : MasbahaAction
    data object ResetCount : MasbahaAction
    data class SetSelectedAzkar(val azkar: MasbahaAzkar) : MasbahaAction
    data class UpdateTargetCount(val target: Int) : MasbahaAction
    data class UpdateAutoSwitch(val enabled: Boolean) : MasbahaAction
    data class UpdateVibration(val enabled: Boolean) : MasbahaAction
    data class UpdateSound(val enabled: Boolean) : MasbahaAction
}