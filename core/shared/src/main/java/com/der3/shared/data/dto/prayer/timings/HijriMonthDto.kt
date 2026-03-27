package com.der3.shared.data.dto.prayer.timings

import kotlinx.serialization.Serializable

@Serializable
data class HijriMonthDto(
    val number: Int,
    val en: String,
    val ar: String,
    val days: Int
)