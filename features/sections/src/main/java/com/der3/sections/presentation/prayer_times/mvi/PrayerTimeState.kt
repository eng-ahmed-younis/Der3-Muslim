package com.der3.sections.presentation.prayer_times.mvi

import androidx.compose.runtime.Immutable
import com.der3.mvi.MviState
import com.der3.shared.data.dto.prayer.timings.PrayerTimesDto

@Immutable
data class PrayerTimeState(
    val isLoading: Boolean = false,
    val locationName: String = "",
    val hijriDate: String = "",
    val gregorianDate: String = "",
    val nextPrayer: PrayerDetails? = null,
    val prayerTimes: List<PrayerDetails> = emptyList(),
    val calculationMethods: List<CalculationMethod> = emptyList(),
    val selectedMethodId: Int = 4, // Default Umm Al-Qura
    val selectedSchoolId: Int = 0, // 0: Shafi, 1: Hanafi
    val is24HourFormat: Boolean = false,
    val latitude: Double = 24.7136,
    val longitude: Double = 46.6753,
    val monthlyCalendar: List<PrayerTimesDto> = emptyList(),
    val error: String? = null
) : MviState

data class CalculationMethod(
    val id: Int,
    val name: String,
    val description: String? = null
)

@Immutable
data class PrayerDetails(
    val name: String,
    val time: String,
    val remainingTime: String = "00:00:00",
    val isNext: Boolean = false,
    val isPassed: Boolean = false,
    val notificationEnabled: Boolean = true,
    val type: PrayerType
)

enum class PrayerType {
    FAJR, SUNRISE, DHUHR, ASR, MAGHRIB, ISHA, IMSAK, MIDNIGHT, FIRST_THIRD, LAST_THIRD
}
