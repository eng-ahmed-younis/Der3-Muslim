package com.der3.home.presentations.side_menu.setting.mvi

import com.der3.mvi.MviIntent

sealed class SettingIntent : MviIntent {
    data class OnNightModeChange(val isNightMode: Boolean) : SettingIntent()
    data class OnFontSizeChange(val fontSize: Int) : SettingIntent()
    data class OnAutoPlayChange(val isAutoPlayEnabled: Boolean) : SettingIntent()
    data class OnPlaybackSpeedChange(val speed: Float) : SettingIntent()
    data class OnMorningRemindersChange(val isEnabled: Boolean) : SettingIntent()
    data class OnEveningRemindersChange(val isEnabled: Boolean) : SettingIntent()
    data class OnSleepingRemindersChange(val isEnabled: Boolean) : SettingIntent()
    object Back : SettingIntent()
    object Retry : SettingIntent()
    object DismissError : SettingIntent()
    object ShareApp : SettingIntent()
    object RateApp : SettingIntent()
    object AboutApp : SettingIntent()
}
