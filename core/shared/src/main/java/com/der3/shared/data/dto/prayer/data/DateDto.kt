package com.der3.shared.data.dto.prayer.data

import kotlinx.serialization.Serializable

@Serializable
data class DateDto(
    val readable: String,
    val timestamp: String,
    val hijri: HijriDateDto,
    val gregorian: GregorianDateDto
)