package com.der3.home.presentations.side_menu.setting.mvi

import com.der3.mvi.MviAction

sealed class SettingAction : MviAction {
    data class UpdateNightMode(val isNightMode: Boolean) : SettingAction()
    data class UpdateFontSize(val fontSize: Int) : SettingAction()
    data class UpdateAutoPlay(val isAutoPlayEnabled: Boolean) : SettingAction()
    data class UpdatePlaybackSpeed(val speed: Float) : SettingAction()
    data class UpdateMorningReminders(val isEnabled: Boolean) : SettingAction()
    data class UpdateEveningReminders(val isEnabled: Boolean) : SettingAction()
    data class UpdateSleepingReminders(val isEnabled: Boolean) : SettingAction()
    data class UpdateLoading(val isLoading: Boolean) : SettingAction()
    data class UpdateError(val error: String?) : SettingAction()

    data class UpdateUseDarkStatusBarIcons(val useDarkStatusBarIcons: Boolean) : SettingAction()
}
