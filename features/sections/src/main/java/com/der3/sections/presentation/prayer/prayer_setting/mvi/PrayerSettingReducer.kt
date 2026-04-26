package com.der3.sections.presentation.prayer.prayer_setting.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class PrayerSettingReducer @Inject constructor(): Reducer<PrayerSettingAction, PrayerSettingState> {
    override fun reduce(action: PrayerSettingAction, state: PrayerSettingState): PrayerSettingState {
        return when (action) {
            is PrayerSettingAction.Loading -> state.copy(isLoading = action.isLoading)
            is PrayerSettingAction.SettingsLoaded -> state.copy(
                locationName = action.locationName,
                selectedMethodId = action.selectedMethodId,
                selectedMadhab = action.selectedMadhab,
                highLatitudeAdjustment = action.highLatitudeAdjustment,
                manualOffsets = action.manualOffsets,
                isLoading = false
            )
            is PrayerSettingAction.UpdateMethod -> state.copy(selectedMethodId = action.methodId)
            is PrayerSettingAction.UpdateMadhab -> state.copy(selectedMadhab = action.madhab)
            is PrayerSettingAction.UpdateHighLatitudeMethod -> state.copy(highLatitudeAdjustment = action.method)
            is PrayerSettingAction.UpdateOffset -> {
                val updatedOffsets = state.manualOffsets.toMutableMap().apply {
                    put(action.prayer, action.offset)
                }
                state.copy(manualOffsets = updatedOffsets)
            }
            is PrayerSettingAction.Saving -> state.copy(isSaving = action.isSaving)
            is PrayerSettingAction.Error -> state.copy(error = action.message, isLoading = false, isSaving = false)
        }
    }
}
