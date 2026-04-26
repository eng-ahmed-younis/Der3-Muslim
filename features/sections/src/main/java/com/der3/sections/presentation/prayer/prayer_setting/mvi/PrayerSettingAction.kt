package com.der3.sections.presentation.prayer.prayer_setting.mvi

import com.der3.mvi.MviAction

sealed class PrayerSettingAction : MviAction {
    data class Loading(val isLoading: Boolean) : PrayerSettingAction()
    data class SettingsLoaded(
        val locationName: String,
        val selectedMethodId: Int,
        val selectedMadhab: Madhab,
        val highLatitudeAdjustment: String,
        val manualOffsets: Map<PrayerType, Int>
    ) : PrayerSettingAction()
    data class UpdateMethod(val methodId: Int) : PrayerSettingAction()
    data class UpdateMadhab(val madhab: Madhab) : PrayerSettingAction()
    data class UpdateHighLatitudeMethod(val method: String) : PrayerSettingAction()
    data class UpdateOffset(val prayer: PrayerType, val offset: Int) : PrayerSettingAction()
    data class Saving(val isSaving: Boolean) : PrayerSettingAction()
    data class Error(val message: String) : PrayerSettingAction()
}
