package com.der3.sections.presentation.prayer.prayer_setting.mvi

import com.der3.mvi.MviIntent

sealed class PrayerSettingIntent : MviIntent {
    object LoadSettings : PrayerSettingIntent()
    object ChangeLocation : PrayerSettingIntent()
    data class SelectCalculationMethod(val methodId: Int) : PrayerSettingIntent()
    data class SelectMadhab(val madhab: Madhab) : PrayerSettingIntent()
    data class SelectHighLatitudeMethod(val method: String) : PrayerSettingIntent()
    data class UpdateManualAdjustment(val prayer: PrayerType, val offset: Int) : PrayerSettingIntent()
    object SaveSettings : PrayerSettingIntent()
    object Back : PrayerSettingIntent()
    object Retry : PrayerSettingIntent()
}
