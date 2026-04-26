package com.der3.sections.presentation.prayer.prayer_setting.mvi

import com.der3.mvi.MviState

data class PrayerSettingState(
    val isLoading: Boolean = false,
    val locationName: String = "",
    val calculationMethods: List<CalculationMethodUi> = emptyList(),
    val selectedMethodId: Int = 2,
    val selectedMadhab: Madhab = Madhab.SHAFI,
    val highLatitudeAdjustment: String = "Angle Based",
    val manualOffsets: Map<PrayerType, Int> = PrayerType.values().associateWith { 0 },
    val isSaving: Boolean = false,
    val error: String? = null
) : MviState

data class CalculationMethodUi(
    val id: Int,
    val name: String,
    val isSelected: Boolean = false
)

enum class Madhab(val value: Int) {
    SHAFI(0),
    HANAFI(1)
}

enum class PrayerType {
    FAJR,
    DHUHR,
    ASR,
    MAGHRIB,
    ISHA
}
