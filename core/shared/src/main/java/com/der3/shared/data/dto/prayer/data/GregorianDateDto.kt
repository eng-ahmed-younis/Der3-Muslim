package com.der3.shared.data.dto.prayer.data


import com.der3.shared.data.dto.prayer.timings.GregorianMonthDto
import com.der3.shared.data.dto.prayer.timings.WeekdayEnDto
import kotlinx.serialization.Serializable

@Serializable
data class GregorianDateDto(
    val date: String,
    val day: String,
    val weekday: WeekdayEnDto,
    val month: GregorianMonthDto,
    val year: String
)