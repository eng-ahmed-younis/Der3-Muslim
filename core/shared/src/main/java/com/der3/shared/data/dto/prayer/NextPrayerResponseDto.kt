package com.der3.shared.data.dto.prayer

import com.der3.shared.data.dto.prayer.data.DateDto
import kotlinx.serialization.Serializable

@Serializable
data class NextPrayerResponseDto(
    val code: Int,
    val status: String,
    val data: NextPrayerDto
)

@Serializable
data class NextPrayerDto(
    val timings: Map<String, String>,
    val date: DateDto
)
