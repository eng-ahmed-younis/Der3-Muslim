package com.der3.sections.domain.model

data class PrayerStatusInfo(
    val name: String,
    val time: String,
    val timeAmPm: String,
    val status: PrayerStatus,
    val remainingMinutes: Int
)