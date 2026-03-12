package com.der3.sections.presentation.prayer_times.mvi

import androidx.compose.runtime.Immutable
import com.der3.mvi.MviState

@Immutable
data class PrayerTimeState(
    val isLoading: Boolean = false,
    val locationName: String = "",
    val hijriDate: String = "",
    val gregorianDate: String = "",
    val nextPrayer: PrayerDetails? = null,
    val prayerTimes: List<PrayerDetails> = emptyList(),
    val error: String? = null
) : MviState

@Immutable
data class PrayerDetails(
    val name: String,
    val time: String,
    val isNext: Boolean = false,
    val isPassed: Boolean = false,
    val notificationEnabled: Boolean = true,
    val type: PrayerType
)

enum class PrayerType {
    FAJR, SUNRISE, DHUHR, ASR, MAGHRIB, ISHA
}
