package com.der3.sections.presentation.prayer.prayer_times.mvi

import androidx.compose.runtime.Immutable
import com.der3.mvi.MviState
import com.der3.sections.domain.model.PrayerDetails
import com.der3.shared.data.dto.prayer.timings.PrayerTimesDto

@Immutable
data class PrayerTimeState(
    val isLoading: Boolean = false,
    val locationName: String = "",
    val hijriDate: String = "",
    val gregorianDate: String = "",
    val nextPrayer: PrayerDetails? = null,
    val prayerTimes: List<PrayerDetails> = emptyList(),
    val selectedMethodId: Int = 4, // Default Umm Al-Qura
    val selectedSchoolId: Int = 0, // 0: Shafi, 1: Hanafi
    val is24HourFormat: Boolean = false,
    val latitude: Double = 24.7136,
    val longitude: Double = 46.6753,
    val monthlyCalendar: List<PrayerTimesDto> = emptyList(),
    val error: String? = null
) : MviState




