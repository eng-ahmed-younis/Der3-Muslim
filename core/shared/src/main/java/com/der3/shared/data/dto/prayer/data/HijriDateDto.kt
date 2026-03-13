package com.der3.shared.data.dto.prayer.data

import com.der3.shared.data.dto.prayer.timings.HijriMonthDto
import com.der3.shared.data.dto.prayer.timings.WeekdayArDto
import kotlinx.serialization.Serializable

@Serializable
data class HijriDateDto(
    val date: String,
    val day: String,
    val weekday: WeekdayArDto,
    val month: HijriMonthDto,
    val year: String,
    val holidays: List<String>? = null
)