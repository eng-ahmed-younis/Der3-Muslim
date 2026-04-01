package com.der3.home.presentations.side_menu.setting.mvi

import com.der3.mvi.Reducer

class SettingReducer : Reducer<SettingAction, SettingState> {
    override fun reduce(action: SettingAction, state: SettingState): SettingState {
        return when (action) {
            is SettingAction.UpdateNightMode -> state.copy(isNightMode = action.isNightMode)
            is SettingAction.UpdateFontSize -> state.copy(fontSize = action.fontSize)
            is SettingAction.UpdateAutoPlay -> state.copy(isAutoPlayEnabled = action.isAutoPlayEnabled)
            is SettingAction.UpdatePlaybackSpeed -> {
                state.copy(playbackSpeed = action.speed)
            }
            is SettingAction.UpdateMorningReminders -> state.copy(isMorningRemindersEnabled = action.isEnabled)
            is SettingAction.UpdateEveningReminders -> state.copy(isEveningRemindersEnabled = action.isEnabled)
            is SettingAction.UpdateSleepingReminders -> state.copy(isSleepingRemindersEnabled = action.isEnabled)
            is SettingAction.UpdateLoading -> state.copy(isLoading = action.isLoading)
            is SettingAction.UpdateError -> state.copy(error = action.error)
            is SettingAction.UpdateUseDarkStatusBarIcons -> state.copy(useDarkStatusBarIcons = action.useDarkStatusBarIcons)
        }
    }
}
