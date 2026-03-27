package com.der3.shared.data.dto.prayer.timings

import com.der3.shared.data.dto.prayer.data.DateDto
import com.der3.shared.data.dto.prayer.meta.MetaDto
import kotlinx.serialization.Serializable

@Serializable
data class PrayerTimesDto(
    val timings: TimingsDto,
    val date: DateDto,
    val meta: MetaDto
)