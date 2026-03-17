package com.der3.sections.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class PrayerDetails(
    val name: String,
    val time: String,
    val remainingTime: String = "00:00:00",
    val isNext: Boolean = false,
    val isPassed: Boolean = false,
    val notificationEnabled: Boolean = true,
    val type: PrayerType
)