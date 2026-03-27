package com.der3.shared.data.dto.prayer.base

import com.der3.shared.data.dto.prayer.timings.PrayerTimesDto
import kotlinx.serialization.Serializable

@Serializable
data class CalendarResponseDto(
    val code: Int,
    val status: String,
    val data: List<PrayerTimesDto>
)